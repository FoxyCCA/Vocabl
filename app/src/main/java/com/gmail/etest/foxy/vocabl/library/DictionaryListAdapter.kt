package com.gmail.etest.foxy.vocabl.library

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.gmail.etest.foxy.vocabl.R
import com.gmail.etest.foxy.vocabl.database.DictionaryModel

class DictionaryListAdapter(private val context: Context, private val dictionaryList: List<DictionaryModel>):
    ArrayAdapter<DictionaryModel>(context, R.layout.list_dictionary, dictionaryList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = LayoutInflater.from(context).inflate(R.layout.list_dictionary, null)

        val dictionaryNameView = view.findViewById<TextView>(R.id.textViewDictionaryName)
        val dictionaryLanguageFromView = view.findViewById<TextView>(R.id.textViewLanguageFrom)
        val dictionaryLanguageToView = view.findViewById<TextView>(R.id.textViewLanguageTo)
        val dictionaryCountView = view.findViewById<TextView>(R.id.textViewEntryNumber)

        var newName = ""
        if (dictionaryList[position].name.length >= 15){
            newName = dictionaryList[position].name.slice(0..15) + "..."
        } else {
            newName = dictionaryList[position].name
        }
        dictionaryNameView.text = newName
        dictionaryLanguageFromView.text = dictionaryList[position].languageFrom
        dictionaryLanguageToView.text = dictionaryList[position].languageTo
        dictionaryCountView.text = dictionaryList[position].count.toString()
        return view
    }
}