package com.gmail.etest.foxy.vocabl.entry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.gmail.etest.foxy.vocabl.R
import com.gmail.etest.foxy.vocabl.database.DictionaryEntryModel
import com.gmail.etest.foxy.vocabl.library.DictionaryListAdapter

class EntryListAdapter(private val context: Context, private val entryList: List<DictionaryEntryModel>):
ArrayAdapter<DictionaryEntryModel>(context, R.layout.list_entry, entryList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = LayoutInflater.from(context).inflate(R.layout.list_entry, null)

        val entryNameView = view.findViewById<TextView>(R.id.textViewEntryName)

        var newName = ""
        if (entryList[position].entryName.length >= 20){
            newName = entryList[position].entryName.slice(0..20) + "..."
        } else {
            newName = entryList[position].entryName
        }
        entryNameView.text = newName

        return view
    }
}