package com.gmail.etest.foxy.vocabl.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.gmail.etest.foxy.vocabl.R
import com.gmail.etest.foxy.vocabl.database.Database
import com.gmail.etest.foxy.vocabl.review.Review

class HomeFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        updateCounts()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        initComponents(rootView)

        return rootView
    }

    private fun initComponents(rootView: View){
        val cardReview = rootView.findViewById<CardView>(R.id.cardViewReview)

        updateCounts()

        cardReview.setOnClickListener {
            val reviewIntent = Intent(context, Review::class.java)
            startActivity(reviewIntent)
        }
    }

    private fun updateCounts() {
        val context = requireContext()
        val db = Database(context)
        val newEntryCountTextView = view?.findViewById<TextView>(R.id.textViewNewCount)
        val learningCountTextView = view?.findViewById<TextView>(R.id.textViewLearningCount)
        val memorizedCountTextView = view?.findViewById<TextView>(R.id.textViewMemorizedCount)

        val needToReviewTextView = view?.findViewById<TextView>(R.id.textViewNeedToReview)
        val entryList = db.getEntriesByDate()

        if (entryList != null){
            val newCount = entryList.count { it.status == "New" }
            val learningCount = entryList.count { it.status == "Learning" }
            val memorizedCount = entryList.count { it.status == "Memorized" }

            newEntryCountTextView?.text = newCount.toString()
            learningCountTextView?.text = learningCount.toString()
            memorizedCountTextView?.text = memorizedCount.toString()
        } else {
            val newText = "You have no entries\nto review"
            val zeroText = "0"
            needToReviewTextView?.text = newText
            newEntryCountTextView?.text = zeroText
            learningCountTextView?.text = zeroText
            memorizedCountTextView?.text = zeroText
        }
    }


}