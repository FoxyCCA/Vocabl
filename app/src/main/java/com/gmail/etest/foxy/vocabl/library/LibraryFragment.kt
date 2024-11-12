package com.gmail.etest.foxy.vocabl.library

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.gmail.etest.foxy.vocabl.R
import com.gmail.etest.foxy.vocabl.database.Database
import com.gmail.etest.foxy.vocabl.database.DictionaryEntryModel
import com.gmail.etest.foxy.vocabl.entry.EntryListAdapter
import com.gmail.etest.foxy.vocabl.entry.UpdateEntry

class LibraryFragment : Fragment() {

    private var entryList: ArrayList<DictionaryEntryModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_library, container, false)

        initComponents(rootView)

        return rootView
    }

    private fun initComponents(rootView: View) {
        val context: Context = requireContext()
        val db = Database(context)

        val dictionaryNames = db.getDictionaryNames()
        val autoCompleteTextView = rootView.findViewById<AutoCompleteTextView>(R.id.autoCompleteDictionarySelect)
        val listView = rootView.findViewById<ListView>(R.id.listViewEntry)

        var dictId = 0
        if (dictionaryNames != null){
            entryList = db.getEntriesById(dictionaryNames.values.toList()[0])

            autoCompleteTextView.setText(dictionaryNames.keys.toList()[0])
            listView.adapter = EntryListAdapter(context, entryList!!)

            val dialog = Dialog(context)

            if(dictionaryNames.isNotEmpty()){
                val adapter = ArrayAdapter(context, R.layout.library_list_item, dictionaryNames.keys.toList())
                autoCompleteTextView.setAdapter(adapter)
            }

            autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                dictId = dictionaryNames[autoCompleteTextView.adapter.getItem(position).toString()]!!

                entryList = db.getEntriesById(dictId)
                if(!entryList.isNullOrEmpty()){
                    listView.adapter = EntryListAdapter(context, entryList!!)
                } else {
                    listView.adapter = null
                }
            }


            updateData(context, listView)
            entrySorting(rootView, context, entryList, listView)
            entryDeletionDialog(context, dialog, listView, db, dictId, entryList!!)
        }
    }

    private fun updateData(context: Context, listView: ListView) {
        listView.setOnItemClickListener { _, _, position, _ ->
            val dictEntry: DictionaryEntryModel = listView.adapter.getItem(position) as DictionaryEntryModel

            val otherData =
                "Entry Id: ${dictEntry.entryId}\n" +
                        "Status: ${dictEntry.status}\n" +
                        "Repetitions: ${dictEntry.repetitions}\n" +
                        "Interval: ${dictEntry.previousInterval}\n" +
                        "Ease Factor: ${dictEntry.previousEaseFactor}\n" +
                        "Creation Date: ${dictEntry.creationDate}\n" +
                        "Review Date: ${dictEntry.reviewDate}\n" +
                        "Update Date: ${dictEntry.updateDate}"

            val updateIntent = Intent(context, UpdateEntry::class.java)

            updateIntent.putExtra("entry_id", dictEntry.entryId)
            updateIntent.putExtra("entry_name", dictEntry.entryName)
            updateIntent.putExtra("entry_description", dictEntry.entryDescription)
            updateIntent.putExtra("other_data", otherData)

            startActivity(updateIntent)
        }
    }


    private fun entrySorting(rootView: View, context: Context, entryList: ArrayList<DictionaryEntryModel>?, listView: ListView) {
        val autoCompleteTextViewSort = rootView.findViewById<AutoCompleteTextView>(R.id.autoCompleteSort)

        val sortList = listOf("A-Z", "Z-A")
        autoCompleteTextViewSort.setAdapter(ArrayAdapter(context, R.layout.library_list_item, sortList))

        autoCompleteTextViewSort.setOnItemClickListener { _, _, position, _ ->
            if (entryList != null){
                autoCompleteTextViewSort.text = null

                when(autoCompleteTextViewSort.adapter.getItem(position).toString()){
                    "A-Z" -> entryList.sortWith(compareBy{ it.entryName })
                    "Z-A" -> entryList.sortWith(compareByDescending { it.entryName })
                }

                if(entryList.isNotEmpty()){
                    val adapter = EntryListAdapter(context, entryList)
                    listView.adapter = adapter
                }
            }
        }
    }

    private fun entryDeletionDialog(context: Context, dialog: Dialog, listView: ListView, db: Database, dictId: Int, list: ArrayList<DictionaryEntryModel> ) {
        var entryList = list
        listView.setOnItemLongClickListener { _, _, position, _ ->
            dialog.setContentView(R.layout.dialog_deletion)

            dialog.window?.setLayout(650, 400)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.show()

            val newText = "Are you sure you want\nto delete this entry?"
            dialog.findViewById<TextView>(R.id.textViewDeleteText).text = newText

            val buttonDelete = dialog.findViewById<Button>(R.id.buttonDeleteDictionary)
            val buttonCancel = dialog.findViewById<Button>(R.id.buttonCancelDeletionOfDictionary)

            buttonDelete.setOnClickListener {
                val entry: DictionaryEntryModel = listView.adapter.getItem(position) as DictionaryEntryModel
                db.deleteEntryById(entry.entryId!!, dictId)
                val entries = db.getEntriesById(dictId)

                if(entries != null){
                    entryList = entries
                }

                if(entryList.isNotEmpty()){
                    listView.adapter = EntryListAdapter(context, entryList)
                } else {
                    listView.adapter = null
                }
                dialog.dismiss()
            }

            buttonCancel.setOnClickListener {
                dialog.dismiss()
            }
            true
        }
    }

}