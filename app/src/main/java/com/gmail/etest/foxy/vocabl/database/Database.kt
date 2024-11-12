package com.gmail.etest.foxy.vocabl.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.TextUtils
import android.widget.Toast
import androidx.core.database.getStringOrNull
import java.util.Date

class Database(val context: Context): SQLiteOpenHelper(context, "Vocabl.sqlite", null, 15) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Creating the Dictionary Table
        val dictionaryTableQuery =
            "CREATE TABLE Dictionary (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                    "name VARCHAR(256) NOT NULL, " +
                    "language_from VARCHAR(128) NOT NULL, " +
                    "language_to VARCHAR(128) NOT NULL, " +
                    "translation_suggestions BOOLEAN NOT NULL DEFAULT 1, " +
                    "count INTEGER NOT NULL DEFAULT 0, " +
                    "entry_id INTEGER, " +
                    "creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, "+
                    "update_date DATETIME, " +
                    "FOREIGN KEY(entry_id) REFERENCES Dictionary_Entry(entry_id) ON UPDATE CASCADE ON DELETE CASCADE)"

        db?.execSQL(dictionaryTableQuery)

        // Creating the Dictionary Entry Table
        val dictionaryEntryTableQuery =
            "CREATE TABLE Dictionary_Entry (" +
                    "entry_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                    "entry_name VARCHAR(256) NOT NULL, " +
                    "entry_description VARCHAR(1024) NOT NULL, " +
                    "repetitions INTEGER NOT NULL DEFAULT 0, " +
                    "previous_interval INTEGER NOT NULL DEFAULT 0, " +
                    "previous_ease_factor DOUBLE NOT NULL DEFAULT 2.5, " +
                    "status VARCHAR(50) NOT NULL, " +
                    "creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                    "update_date DATETIME, " +
                    "review_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP)"

        db?.execSQL(dictionaryEntryTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Dictionary")
        db?.execSQL("DROP TABLE IF EXISTS Dictionary_Entry")
        onCreate(db)
    }

    fun createNewDictionary(dictionary: DictionaryModel){
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put("name", dictionary.name)
        cv.put("language_from", dictionary.languageFrom)
        cv.put("language_to", dictionary.languageTo)
        cv.put("translation_suggestions", dictionary.translationSuggestions)

        val result = db.insert("Dictionary", null, cv)
        if (result == (-1).toLong()){
            Toast.makeText(context, "Failed to created Dictionary ${dictionary.name}", Toast.LENGTH_LONG).show()
        }

        db.close()
    }

    @SuppressLint("Range")
    fun getDictionaries(): List<DictionaryModel>? {
        var dictList: ArrayList<DictionaryModel>? = ArrayList()

        val db = this.writableDatabase
        val query = "SELECT * FROM Dictionary"
        val result = db.rawQuery(query, null)

        if(result.moveToFirst()){
            do {

                dictList?.add(DictionaryModel(
                    result.getInt(result.getColumnIndex("id")),
                    result.getString(result.getColumnIndex("name")),
                    result.getString(result.getColumnIndex("language_from")),
                    result.getString(result.getColumnIndex("language_to")),
                    if(result.getString(result.getColumnIndex("translation_suggestions")) == "1") true else false,
                    result.getInt(result.getColumnIndex("count")),
                    if (result.getStringOrNull(result.getColumnIndex("entry_id")) != null){
                        result.getStringOrNull(result.getColumnIndex("entry_id")).toString().split(',').map { it.toInt() }.toList()
                    }  else {
                        null
                    },
                    Date(result.getLong(result.getColumnIndex("creation_date"))),
                    null
                ))

            } while (result.moveToNext())
        } else {
            dictList = null
        }

        result.close()
        db.close()
        return dictList
    }

    fun deleteDictionary(id: Int, entryId: List<Int>?) {
        val db = this.writableDatabase
        db.delete("Dictionary", "id=?", arrayOf(id.toString()))
        if (entryId != null){
            db.execSQL("DELETE FROM Dictionary_Entry WHERE entry_id IN (${TextUtils.join(",", entryId)})")
        }
        db.close()
    }

    fun createNewEntry(entry: DictionaryEntryModel, dictId: Int) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put("entry_name", entry.entryName)
        cv.put("entry_description", entry.entryDescription)
        cv.put("status", entry.status)

        val result = db.insert("Dictionary_Entry", null, cv)

        if (result == (-1).toLong()){
            Toast.makeText(context, "Failed to create Entry ${entry.entryName}", Toast.LENGTH_LONG).show()
        } else {
            var ids: String? = "0"
            var count: Int = 0

            val query = "SELECT last_insert_rowid()"
            val resultId = db.rawQuery(query, null)

            var id = 0
            if(resultId.moveToFirst()){
                id = resultId.getInt(0)
            }

            val idsResult = db.rawQuery("SELECT entry_id, count FROM Dictionary WHERE id = $dictId", null)
            if(idsResult.moveToFirst()) {
                ids = idsResult.getStringOrNull(0)
                count = idsResult.getInt(1) + 1
            }

            if (ids.isNullOrEmpty()){
                db.execSQL("UPDATE Dictionary SET entry_id = '$id', count = $count WHERE id = $dictId")
            } else {
                db.execSQL("UPDATE Dictionary SET entry_id = '$ids,$id', count = $count WHERE id = $dictId")
            }

            idsResult.close()
            resultId.close()
        }

        db.close()
    }

    fun getDictionaryNames(): MutableMap<String, Int>? {
        var dictNameList: MutableMap<String, Int>? = mutableMapOf()

        val db = this.writableDatabase
        val query = "SELECT name, id FROM Dictionary"
        val result = db.rawQuery(query, null)

        if(result.moveToFirst()){
            do {

                if (result.getStringOrNull(0).isNullOrEmpty()){
                    dictNameList = null
                } else {
                    dictNameList?.put(result.getString(0).toString(), result.getInt(1))
                }

            } while (result.moveToNext())
        } else {
            dictNameList = null
        }

        result.close()
        db.close()
        return dictNameList
    }

    @SuppressLint("Range")
    fun getEntriesById(dictId: Int): ArrayList<DictionaryEntryModel>? {
        var entryList: ArrayList<DictionaryEntryModel>? = ArrayList()

        val db = this.writableDatabase
        val query = db.rawQuery("SELECT entry_id FROM Dictionary WHERE id = $dictId", null)
        var ids: String? = null

        if(query.moveToFirst()){
            ids = query.getStringOrNull(0)
        }
        if (ids.isNullOrEmpty()){
            return null
        }

        val result = db.rawQuery("SELECT * FROM Dictionary_Entry WHERE entry_id IN ($ids)", null)
        if(result.moveToFirst()){
            do {

                entryList?.add(
                    DictionaryEntryModel(
                        result.getInt(result.getColumnIndex("entry_id")),
                        result.getString(result.getColumnIndex("entry_name")),
                        result.getString(result.getColumnIndex("entry_description")),
                        result.getInt(result.getColumnIndex("repetitions")),
                        result.getInt(result.getColumnIndex("previous_interval")),
                        result.getDouble(result.getColumnIndex("previous_ease_factor")),
                        result.getString(result.getColumnIndex("status")),
                        Date(result.getLong(result.getColumnIndex("creation_date"))),
                        Date(result.getLong(result.getColumnIndex("review_date")))
                ))

            } while (result.moveToNext())
        } else {
            entryList = null
        }

        result.close()
        db.close()
        return entryList
    }


    @SuppressLint("Range")
    fun getEntriesByDate(): ArrayList<DictionaryEntryModel>? {
        var entryList: ArrayList<DictionaryEntryModel>? = ArrayList()

        val db = this.writableDatabase
        // date('now') on it's own doesn't work probably because it sets the time to 00:00
        // so if you have an entry made at 14:00 the query would return empty
        val result = db.rawQuery("SELECT * FROM Dictionary_Entry WHERE date(review_date) < date('now', '+24 hours')", null)

        if(result.moveToFirst()){
            do {

                entryList?.add(
                    DictionaryEntryModel(
                        result.getInt(result.getColumnIndex("entry_id")),
                        result.getString(result.getColumnIndex("entry_name")),
                        result.getString(result.getColumnIndex("entry_description")),
                        result.getInt(result.getColumnIndex("repetitions")),
                        result.getInt(result.getColumnIndex("previous_interval")),
                        result.getDouble(result.getColumnIndex("previous_ease_factor")),
                        result.getString(result.getColumnIndex("status")),
                        Date(result.getLong(result.getColumnIndex("creation_date"))),
                        Date(result.getLong(result.getColumnIndex("review_date")))
                    ))

            } while (result.moveToNext())
        } else {
            entryList = null
        }

        result.close()
        db.close()
        return entryList
    }

    fun checkIfEntryNameAlreadyExists(entryName: String, ids: String): Boolean {
        val db = this.writableDatabase
        val result = db.rawQuery("SELECT entry_id FROM Dictionary_Entry WHERE entry_name = \"$entryName\"", null)

        var nameExists: Boolean
        var entryId: Int = 0
        if(result.moveToFirst()){
            entryId = result.getInt(0)
        } else {
            nameExists = false
        }

        if(ids.contains(',')){
            if(ids.contains("$entryId,")){
                nameExists = true
            } else {
                nameExists = false
            }
        } else {
            if(ids.contains("$entryId]")){
                nameExists = true
            } else {
                nameExists = false
            }
        }
        result.close()
        db.close()

        return nameExists
    }


    fun updateReviewDate(entry: DictionaryEntryModel){

        val db = this.writableDatabase
        db.execSQL(
            "UPDATE Dictionary_Entry SET " +
                    "repetitions = ${entry.repetitions}, " +
                    "previous_interval = ${entry.previousInterval}, " +
                    "previous_ease_factor = ${entry.previousEaseFactor}, " +
                    "review_date = date(review_date, '+${entry.previousInterval} days'), " +
                    "status = '${if (entry.previousInterval<180) "Learning" else "Memorized"}', " +
                    "update_date = date('now') " +
                    "WHERE entry_id = ${entry.entryId}"
        )

        db.close()
    }

    fun updateEntryById(entryId: Int, entryDescription: String){
        val db = this.writableDatabase

        db.execSQL("UPDATE Dictionary_Entry SET entry_description = \"${entryDescription}\" WHERE entry_id = $entryId")

        db.close()
    }


    fun deleteEntryById(id: Int, dictId: Int){
        val db = this.writableDatabase
        val result = db.rawQuery("SELECT entry_id, count FROM Dictionary WHERE id = $dictId", null)
        var ids: String? = null
        var count = 0

        if(result.moveToFirst()){
            ids = result.getStringOrNull(0)
            count = result.getInt(1) - 1
        }

        if(!ids.isNullOrEmpty()){
            if(ids.indexOf(id.toString()) == 0){
                ids = ids.replace("$id", "")
            } else {
                ids = ids.replace(",$id", "")
            }

            if(ids.isEmpty()){
                db.execSQL("UPDATE Dictionary SET entry_id = NULL, count = $count WHERE id = $dictId")
            } else {
                db.execSQL("UPDATE Dictionary SET entry_id = '$ids', count = $count WHERE id = $dictId")
            }

            db.delete("Dictionary_Entry", "entry_id=?", arrayOf(id.toString()))
        }

        result.close()
        db.close()
    }
}