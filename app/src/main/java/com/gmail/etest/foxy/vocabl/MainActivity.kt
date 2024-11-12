package com.gmail.etest.foxy.vocabl

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.gmail.etest.foxy.vocabl.databinding.ActivityMainBinding
import com.gmail.etest.foxy.vocabl.home.HomeFragment
import com.gmail.etest.foxy.vocabl.entry.LibraryAddSelector
import com.gmail.etest.foxy.vocabl.library.LibraryFragment
import com.gmail.etest.foxy.vocabl.settings.SettingsFragment
import com.gmail.etest.foxy.vocabl.statistics.StatisticsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.background = null

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> openFragment(HomeFragment())
                R.id.library -> openFragment(LibraryFragment())
                R.id.statistics -> openFragment(StatisticsFragment())
                R.id.settings -> openFragment(SettingsFragment())
            }
            true
        }
        fragmentManager = supportFragmentManager
        openFragment(HomeFragment())

        binding.floatingButton.setOnClickListener{
            val newIntent = Intent(this, LibraryAddSelector::class.java)
            startActivity(newIntent)
        }

    }


    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}