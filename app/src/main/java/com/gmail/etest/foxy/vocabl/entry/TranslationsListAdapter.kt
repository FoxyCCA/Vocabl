package com.gmail.etest.foxy.vocabl.entry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.gmail.etest.foxy.vocabl.R
import com.gmail.etest.foxy.vocabl.api.Translations

class TranslationsListAdapter(private val context: Context, private val translationsList: List<Translations>):
ArrayAdapter<Translations>(context, R.layout.list_translations, translationsList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = LayoutInflater.from(context).inflate(R.layout.list_translations, null)

        val translatedTextView = view.findViewById<TextView>(R.id.textViewTranslatedText)
        val posTagView = view.findViewById<TextView>(R.id.textViewPosTag)
        val synonymsView = view.findViewById<TextView>(R.id.textViewTranslationSynonyms)

        translatedTextView.text = translationsList[position].displayTarget
        posTagView.text = translationsList[position].posTag

        var translationSynonyms = ""
        for (backTranslations in translationsList[position].backTranslations){
            translationSynonyms += "${backTranslations.displayText}, "
        }
        synonymsView.text = translationSynonyms.dropLast(2)

        return view
    }
}