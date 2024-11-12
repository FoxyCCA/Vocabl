package com.gmail.etest.foxy.vocabl.review

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gmail.etest.foxy.vocabl.R
import com.gmail.etest.foxy.vocabl.database.DictionaryEntryModel
import com.gmail.etest.foxy.vocabl.databinding.ActivityReviewEndBinding

class ReviewEnd : AppCompatActivity() {

    private lateinit var binding: ActivityReviewEndBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewEndBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {
        val entryNameList = intent.getStringArrayListExtra("entry_name_list")
        val entryIntervalList = intent.getIntegerArrayListExtra("entry_interval_list")


        val reviewEndList = ArrayList<String>()
        if(entryIntervalList != null && entryNameList != null){
            val counter = entryNameList.count().toString()

            binding.textViewCounter.text = counter

            for(i in 0..<entryNameList.count()){
                reviewEndList.add("\"${entryNameList[i]}\"\tIn ${entryIntervalList[i]} days")
            }

            binding.listViewReviewEnd.adapter = ArrayAdapter(this, R.layout.library_list_item, reviewEndList)
        }

        binding.buttonFinish.setOnClickListener {
            finish()
        }
    }
}