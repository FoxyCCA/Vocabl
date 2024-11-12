package com.gmail.etest.foxy.vocabl.library

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.gmail.etest.foxy.vocabl.R
import com.gmail.etest.foxy.vocabl.database.Database
import com.gmail.etest.foxy.vocabl.database.DictionaryModel
import com.gmail.etest.foxy.vocabl.databinding.ActivityNewDictionaryBinding
import com.gmail.etest.foxy.vocabl.database.getLanguages

class NewDictionary : AppCompatActivity() {

    private lateinit var binding: ActivityNewDictionaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewDictionaryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {
        val dialog = Dialog(this)
        val db = Database(this)

        val languageSelector1 = binding.textViewLanguageSelector1
        val languageSelector2 = binding.textViewLanguageSelector2

        dialogLanguageBox(languageSelector1, dialog)
        dialogLanguageBox(languageSelector2, dialog)


        binding.buttonSwitchLanguage.setOnClickListener{
            if(languageSelector1.text.isNotEmpty() && languageSelector2.text.isNotEmpty())
                languageSelector1.text = languageSelector2.text.also {
                    languageSelector2.text = languageSelector1.text
                }
        }

        lowerButtons(db, languageSelector1, languageSelector2)
    }


    private fun lowerButtons(db: Database, languageSelector1: TextView, languageSelector2: TextView){
        binding.buttonCreateDictionary.setOnClickListener {
            val name = binding.textInputDictionaryName.text.toString()
            val languageFrom = languageSelector1.text.toString()
            val languageTo = languageSelector2.text.toString()
            val translationSuggestion = binding.switchTranslationSuggestion.isChecked

            if(languageSelector1.text.isNotEmpty() && languageSelector2.text.isNotEmpty() && name.isNotEmpty()){
                val dictionary = DictionaryModel(null, name, languageFrom, languageTo, translationSuggestion,0, null)

                db.createNewDictionary(dictionary)
                finish()
            } else {
                Toast.makeText(this, "Data cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }

    }

    private fun dialogLanguageBox(textView: TextView, dialog: Dialog) {
        val languageList = getLanguages()
        textView.setOnClickListener{

            dialog.setContentView(R.layout.dialog_language_spinner)

            dialog.window?.setLayout(650, 800)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.show()

            val editText: EditText = dialog.findViewById(R.id.language_search)
            val listView: ListView = dialog.findViewById(R.id.language_list)

            val languagesAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, languageList)

            listView.adapter = languagesAdapter

            editText.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(text: Editable?) {
                }

                override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                    languagesAdapter.filter.filter(text)
                }
            })

            listView.setOnItemClickListener { _, _, position, _ ->
                textView.setText(languagesAdapter.getItem(position))

                dialog.dismiss()
            }
        }

    }
}