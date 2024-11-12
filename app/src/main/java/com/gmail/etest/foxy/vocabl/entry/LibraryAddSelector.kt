package com.gmail.etest.foxy.vocabl.entry

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.gmail.etest.foxy.vocabl.R
import com.gmail.etest.foxy.vocabl.database.Database
import com.gmail.etest.foxy.vocabl.database.DictionaryModel
import com.gmail.etest.foxy.vocabl.database.getDictionaryBooleanFromLanguage
import com.gmail.etest.foxy.vocabl.database.getIsoFromLanguage
import com.gmail.etest.foxy.vocabl.databinding.ActivityLibraryAddSelectorBinding
import com.gmail.etest.foxy.vocabl.library.DictionaryListAdapter
import com.gmail.etest.foxy.vocabl.library.NewDictionary

class LibraryAddSelector : AppCompatActivity() {

    private lateinit var binding: ActivityLibraryAddSelectorBinding
    private lateinit var fragmentManager: FragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLibraryAddSelectorBinding.inflate(layoutInflater)

        fragmentManager = supportFragmentManager

        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {
        val db = Database(this)

        binding.buttonCreateNewDictionary.setOnClickListener{
            val newIntent = Intent(this, NewDictionary::class.java)
            startActivity(newIntent)
        }

        binding.addSelectorBackButton.setOnClickListener{
            finish()
        }

        val dictionaries = db.getDictionaries()
        if(dictionaries != null){
            binding.listDictionary.adapter = DictionaryListAdapter(this, dictionaries)
        } else {
            binding.imageViewPointerArrow.visibility = View.VISIBLE
            binding.textViewNewDictionaryHelpText.visibility = View.VISIBLE
        }

        binding.listDictionary.setOnItemClickListener { _, _, position, _ ->
            val dict: DictionaryModel = binding.listDictionary.adapter.getItem(position) as DictionaryModel
            val addIntent = Intent(this, AddEntry::class.java)

            addIntent.putExtra("dictionary_id", dict.id)
            addIntent.putExtra("entry_id", dict.entryId.toString())
            addIntent.putExtra("iso_from", getIsoFromLanguage(dict.languageFrom))
            addIntent.putExtra("iso_to", getIsoFromLanguage(dict.languageTo))
            addIntent.putExtra("language_to", dict.languageTo)
            addIntent.putExtra("translation_suggestions", dict.translationSuggestions)
            addIntent.putExtra("has_dictionary_support", getDictionaryBooleanFromLanguage(dict.languageTo))
            startActivity(addIntent)
        }

        dialogDictionaryBox(db)
    }

    private fun dialogDictionaryBox(db: Database) {
        val dialog = Dialog(this)
        binding.listDictionary.setOnItemLongClickListener { _, _, position, _ ->
            dialog.setContentView(R.layout.dialog_deletion)

            dialog.window?.setLayout(650, 400)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.show()

            val buttonDelete = dialog.findViewById<Button>(R.id.buttonDeleteDictionary)
            val buttonCancel = dialog.findViewById<Button>(R.id.buttonCancelDeletionOfDictionary)

            buttonDelete.setOnClickListener {
                val dict: DictionaryModel = binding.listDictionary.adapter.getItem(position) as DictionaryModel
                db.deleteDictionary(dict.id!!, dict.entryId)
                val dictionaries = db.getDictionaries()
                if(dictionaries != null){
                    binding.listDictionary.adapter = DictionaryListAdapter(this, dictionaries)
                } else {
                    binding.listDictionary.adapter = null
                }
                dialog.dismiss()
            }

            buttonCancel.setOnClickListener {
                dialog.dismiss()
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        val db = Database(this)
        val dictionaries = db.getDictionaries()
        if(dictionaries != null){
            binding.listDictionary.adapter = DictionaryListAdapter(this, dictionaries)
            binding.imageViewPointerArrow.visibility = View.INVISIBLE
            binding.textViewNewDictionaryHelpText.visibility = View.INVISIBLE
        } else {
            binding.imageViewPointerArrow.visibility = View.VISIBLE
            binding.textViewNewDictionaryHelpText.visibility = View.VISIBLE
        }
    }
}