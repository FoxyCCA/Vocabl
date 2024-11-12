package com.gmail.etest.foxy.vocabl.entry

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gmail.etest.foxy.vocabl.api.ApiDictionaryResponse
import com.gmail.etest.foxy.vocabl.api.ApiTranslationResponse
import com.gmail.etest.foxy.vocabl.api.Translations
import com.gmail.etest.foxy.vocabl.api.TranslatorAPI
import com.gmail.etest.foxy.vocabl.database.Database
import com.gmail.etest.foxy.vocabl.database.DictionaryEntryModel
import com.gmail.etest.foxy.vocabl.databinding.ActivityAddBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import java.util.TimerTask

class AddEntry : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private var entryId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {
        val dictId = intent.getIntExtra("dictionary_id", 1)
        entryId = intent.getStringExtra("entry_id")
        val isoFrom = intent.getStringExtra("iso_from")
        val isoTo = intent.getStringExtra("iso_to")
        val languageTo = "In " + intent.getStringExtra("language_to") + ":"
        val translationSuggestions = intent.getBooleanExtra("translation_suggestions", false)
        val hasDictionarySupport = intent.getBooleanExtra("has_dictionary_support", false)

        binding.textViewTranslationTo.text = languageTo
        binding.buttonAddNewEntry.isEnabled = false
        binding.buttonSaveAndNew.isEnabled = false

        binding.buttonCancelNewEntry.setOnClickListener {
            finish()
        }

        val db = Database(this)

        newEntryButtons(db, dictId)

        if(isoFrom != null && isoTo != null){
            if (entryId != null) {
                textInputTranslationChecker(this, isoFrom, isoTo, hasDictionarySupport, translationSuggestions,
                    entryId!!, db)
            }
        }

        if(!translationSuggestions || !hasDictionarySupport){
            binding.textViewTranslationSuggestionsErrorText.visibility = View.VISIBLE
        }
        translationSuggestionsToInputTextAdder()
    }

    private fun translationSuggestionsToInputTextAdder() {
        val listView = binding.listViewTranslations
        listView.setOnItemClickListener { _, _, position, _ ->
            val translatedResult = binding.textInputTranslatedResult

            val translations = listView.adapter.getItem(position) as Translations

            val translatedText: String = translations.displayTarget
            val posTag: String = translations.posTag

            var translationSynonyms = ""
            for (backTranslations in translations.backTranslations){
                translationSynonyms += "${backTranslations.displayText}, "
            }
            translationSynonyms = translationSynonyms.dropLast(2)

            val resultText = "$translatedText - [$posTag] - $translationSynonyms"
            val resultTextSpaced = translatedResult.text.toString() + "\n$translatedText - [$posTag] - $translationSynonyms"

            if(translatedResult.text.isNullOrEmpty()){
                translatedResult.setText(resultText) // had to use setText instead of just .text because the compiler was calling getText instead of setText
            } else {
                translatedResult.setText(resultTextSpaced)
            }
        }
    }

    private fun textInputTranslationChecker(context: Context, isoFrom: String, isoTo: String, hasDictionarySupport: Boolean, translationSuggestions: Boolean, entryId: String, db: Database ) {
        var timer = Timer()
        binding.textInputSentence.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
                if(translationSuggestions && hasDictionarySupport){

                    binding.progressBar.visibility = View.VISIBLE
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            lifecycleScope.launch {


                                binding.listViewTranslations.adapter = null

                                binding.textViewTranslationSuggestionsErrorText.visibility = View.INVISIBLE
                                binding.textViewEntryExistsWarning.visibility = View.INVISIBLE
                                binding.buttonAddNewEntry.isEnabled = true
                                binding.buttonSaveAndNew.isEnabled = true

                                if(db.checkIfEntryNameAlreadyExists(binding.textInputSentence.text.toString(), entryId)
                                    || binding.textInputSentence.text.toString().trim().isEmpty()
                                    || binding.textInputTranslatedResult.toString().trim().isEmpty()){
                                    binding.textViewEntryExistsWarning.visibility = View.VISIBLE
                                    binding.buttonAddNewEntry.isEnabled = false
                                    binding.buttonSaveAndNew.isEnabled = false
                                }

                                if (!binding.textInputSentence.text.isNullOrEmpty() && isoFrom.isNotEmpty() && isoTo.isNotEmpty()) {
                                    if (!binding.textInputSentence.text!!.trim().contains(" ")) { // to check if the input is a word or a sentence

                                        val translationDictionary =
                                            withContext(Dispatchers.IO) {
                                                TranslatorAPI().translatedTextToDictionaryPost(
                                                    binding.textInputSentence.text.toString(),
                                                    isoFrom,
                                                    isoTo,
                                                    hasDictionarySupport
                                                ) as ApiDictionaryResponse
                                            }

                                        if(translationDictionary.translations.isEmpty()){
                                            binding.listViewTranslations.adapter = null

                                            val newErrorText = "${translationDictionary.displaySource} is not in the dictionary"

                                            binding.textViewTranslationSuggestionsErrorText.text = newErrorText
                                            binding.textViewTranslationSuggestionsErrorText.visibility = View.VISIBLE
                                        } else {
                                            binding.listViewTranslations.adapter = TranslationsListAdapter(
                                                context,
                                                translationDictionary.translations
                                            )
                                        }
                                    } else {

                                        val translationText =
                                            withContext(Dispatchers.IO) {
                                                TranslatorAPI().translatedTextToDictionaryPost(
                                                    binding.textInputSentence.text.toString(),
                                                    isoFrom,
                                                    isoTo,
                                                    false
                                                ) as ApiTranslationResponse
                                            }
                                        binding.textInputTranslatedResult.setText(translationText.translations[0].text)
                                        binding.listViewTranslations.adapter = null
                                    }
                                }

                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    }, 1500)
                }
            }
        })
    }

    private fun newEntryButtons(db: Database, dictId: Int) {

        binding.buttonAddNewEntry.setOnClickListener {
            val inputSentence =  binding.textInputSentence.text.toString()
            val inputTranslated =  binding.textInputTranslatedResult.text.toString()
            if (inputTranslated.isNotEmpty() || inputSentence.isNotEmpty()){
                val newEntry = DictionaryEntryModel(null, inputSentence, inputTranslated)

                db.createNewEntry(newEntry, dictId)
                finish()
            }
        }

        binding.buttonSaveAndNew.setOnClickListener {
            val inputSentence =  binding.textInputSentence.text.toString()
            val inputTranslated =  binding.textInputTranslatedResult.text.toString()
            if (inputTranslated.isNotEmpty() || inputSentence.isNotEmpty()){
                val newEntry = DictionaryEntryModel(null, inputSentence, inputTranslated)

                db.createNewEntry(newEntry, dictId)

                binding.textInputSentence.text = null
                binding.textInputTranslatedResult.text = null
            }
        }
    }
}