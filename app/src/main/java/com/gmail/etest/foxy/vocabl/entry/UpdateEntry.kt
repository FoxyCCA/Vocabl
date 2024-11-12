package com.gmail.etest.foxy.vocabl.entry

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gmail.etest.foxy.vocabl.R
import com.gmail.etest.foxy.vocabl.database.Database
import com.gmail.etest.foxy.vocabl.databinding.ActivityUpdateEntryBinding

class UpdateEntry : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateEntryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateEntryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initComponents()

    }

    private fun initComponents() {

        val db = Database(this)

        val entryId = intent.getIntExtra("entry_id", -1)
        val entryName = intent.getStringExtra("entry_name")
        val entryDescription = intent.getStringExtra("entry_description")
        val otherData = intent.getStringExtra("other_data")

        val entryNameTextView = binding.textViewName
        val descriptionTextInput = binding.textInputDescription
        val otherDataTextView = binding.textViewOtherData

        val backButton = binding.buttonUpdateBack
        val updateButton = binding.buttonUpdateEntry


        entryNameTextView.text = entryName
        descriptionTextInput.setText(entryDescription)
        otherDataTextView.text = otherData

        backButton.setOnClickListener {
            finish()
        }

        updateButton.setOnClickListener {
            db.updateEntryById(entryId, descriptionTextInput.text.toString())
            finish()
        }
    }
}