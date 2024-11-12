package com.gmail.etest.foxy.vocabl.review

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.gmail.etest.foxy.vocabl.database.Database
import com.gmail.etest.foxy.vocabl.database.DictionaryEntryModel
import com.gmail.etest.foxy.vocabl.databinding.ActivityReviewBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Timer
import java.util.TimerTask

class Review : AppCompatActivity() {

    private lateinit var binding : ActivityReviewBinding

    private var iterator: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {
        val db = Database(this)

        val entryList: ArrayList<DictionaryEntryModel>? = db.getEntriesByDate()

        val card1 = binding.cardViewCard1
        val card2 = binding.cardViewCard2

        val cardText1 = binding.cardText1
        val cardText2 = binding.cardText2

        val repeatCardView = binding.cardViewRepeat
        val easyCardView = binding.cardViewEasy
        val normalCardView = binding.cardViewNormal
        val hardCardView = binding.cardViewHard

        if (entryList != null){
            cardText1.text = entryList[iterator].entryName
            cardText2.text = entryList[iterator].entryDescription
        } else {
            finish()
        }


        repeatCardView.isEnabled = false
        easyCardView.isEnabled = false
        normalCardView.isEnabled = false
        hardCardView.isEnabled = false

        card1.setOnClickListener{
            repeatCardView.isEnabled = true
            easyCardView.isEnabled = true
            normalCardView.isEnabled = true
            hardCardView.isEnabled = true

            card1.animate().apply {
                duration = 700
                translationY(-180f)
            }

            card2.animate().apply {
                duration = 700
                translationY(300f)
            }

            card1.isEnabled = false
        }

        if (entryList != null){
            var currentEntry = entryList[iterator]
            val entryListLength = entryList.count()

            reassignSpacedRepetitionButtons(currentEntry)

            repeatCardView.setOnClickListener {
                currentEntry = onClickSpacedRepetitionButton(0, currentEntry, card1, card2, db, entryListLength, entryList)
            }

            easyCardView.setOnClickListener {
                currentEntry = onClickSpacedRepetitionButton(5, currentEntry, card1, card2, db, entryListLength, entryList)
            }

            normalCardView.setOnClickListener {
                currentEntry = onClickSpacedRepetitionButton(3, currentEntry, card1, card2, db, entryListLength, entryList)
            }

            hardCardView.setOnClickListener {
                currentEntry = onClickSpacedRepetitionButton(1, currentEntry, card1, card2, db, entryListLength, entryList)
            }
        }
    }

    private fun onClickSpacedRepetitionButton(
        quality: Int,
        currentEntry: DictionaryEntryModel,
        card1: CardView,
        card2: CardView,
        db: Database,
        entryListLength: Int,
        entryList: ArrayList<DictionaryEntryModel>
    ): DictionaryEntryModel {

        val repeatCardView = binding.cardViewRepeat
        val easyCardView = binding.cardViewEasy
        val normalCardView = binding.cardViewNormal
        val hardCardView = binding.cardViewHard

        val cardText1 = binding.cardText1
        val cardText2 = binding.cardText2

        repeatCardView.isEnabled = false
        easyCardView.isEnabled = false
        normalCardView.isEnabled = false
        hardCardView.isEnabled = false

        card1.isEnabled = true


        assignNewReviewDate(
            spacedRepetitionAlgorithm(
                SpacedRepetition(
                    quality,
                    currentEntry.repetitions,
                    currentEntry.previousInterval,
                    currentEntry.previousEaseFactor
                )
            ),
            currentEntry,
            db
        )

        iterator++
        if (entryListLength <= iterator) {
            val reviewEndIntent = Intent(this, ReviewEnd::class.java)

            val entryNameList = ArrayList<String>()
            val entryIntervalList = ArrayList<Int>()

            for (entry in entryList){
                entryNameList.add(entry.entryName)
                entryIntervalList.add(entry.previousInterval)
            }

            reviewEndIntent.putStringArrayListExtra("entry_name_list", entryNameList)
            reviewEndIntent.putIntegerArrayListExtra("entry_interval_list", entryIntervalList)

            startActivity(reviewEndIntent)
            finish()
        } else {


            card1.animate().apply {
                duration = 700
                translationY(0f)
            }

            card2.animate().apply {
                duration = 700
                translationY(0f)
            }


            cardText1.text = entryList[iterator].entryName

            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    lifecycleScope.launch {
                        cardText2.text = entryList[iterator].entryDescription
                    }
                }

            }, 700)
            return entryList[iterator]
        }
        return entryList[0]
    }


    private fun assignNewReviewDate(spacedRepetitionData: SpacedRepetition, entry: DictionaryEntryModel, db: Database) {
        entry.repetitions = spacedRepetitionData.repetitions
        entry.previousInterval = spacedRepetitionData.previousInterval
        entry.previousEaseFactor = spacedRepetitionData.previousEaseFactor


        val date = LocalDate.now().plusDays(spacedRepetitionData.previousInterval.toLong())
        entry.reviewDate = Date.from(
            date.atStartOfDay(
                ZoneId.systemDefault()
            ).toInstant()
        )

        db.updateReviewDate(entry)


    }

    private fun reassignSpacedRepetitionButtons(currentEntry: DictionaryEntryModel){
        val repeatTextView = binding.textViewRepeat
        val easyTextView = binding.textViewEasy
        val normalTextView = binding.textViewNormal
        val hardTextView = binding.textViewHard

        val easyText = "Easy (${formatDayText(
            spacedRepetitionAlgorithm(
                SpacedRepetition(5,
                    currentEntry.repetitions,
                    currentEntry.previousInterval,
                    currentEntry.previousEaseFactor)
            ).previousInterval
        )})"
        val normalText = "Normal (${formatDayText(
            spacedRepetitionAlgorithm(
                SpacedRepetition(3,
                    currentEntry.repetitions,
                    currentEntry.previousInterval,
                    currentEntry.previousEaseFactor)
            ).previousInterval
        )})"
        val hardText = "Hard (${formatDayText(
            spacedRepetitionAlgorithm(
                SpacedRepetition(1,
                    currentEntry.repetitions,
                    currentEntry.previousInterval,
                    currentEntry.previousEaseFactor)
            ).previousInterval
        )})"
        val repeatText = "Repeat (${formatDayText(
            spacedRepetitionAlgorithm(
                SpacedRepetition(0,
                    currentEntry.repetitions,
                    currentEntry.previousInterval,
                    currentEntry.previousEaseFactor)
            ).previousInterval
        )})"

        repeatTextView.text = repeatText
        easyTextView.text = easyText
        normalTextView.text = normalText
        hardTextView.text = hardText
    }

    private fun formatDayText(days: Int): String {
        return if(days < 30){
            "${days}d"

        } else if (days < 365){
            "${(days/40)%.1f}m"

        } else {
            "${(days/365)%.1f}y"
        }
    }
}
