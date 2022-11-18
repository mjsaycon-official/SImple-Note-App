package com.example.simplenoteapp.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.simplenoteapp.R
import com.example.simplenoteapp.adapter.NoteDeleteInterface
import com.example.simplenoteapp.adapter.NoteOpenInterface
import com.example.simplenoteapp.databinding.ActivityMainBinding
import com.example.simplenoteapp.entity.Note
import com.example.simplenoteapp.fragment.AboutFragment
import com.example.simplenoteapp.fragment.AddFragment
import com.example.simplenoteapp.fragment.DashboardFragment
import com.example.simplenoteapp.fragment.ReturnDashboardInterface
import com.example.simplenoteapp.helpers.SharedPrefHelper

class MainActivity : AppCompatActivity(), NoteOpenInterface, ReturnDashboardInterface {

    lateinit var binding: ActivityMainBinding
    val TAG = "MainActivity"

    lateinit var dashboardFragment: DashboardFragment
    lateinit var addFragment: AddFragment
    lateinit var aboutFragment: AboutFragment
    private var isTapDoubled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        SharedPrefHelper.initialize(this)

        dashboardFragment = DashboardFragment()
        addFragment = AddFragment()
        aboutFragment = AboutFragment()

        replaceFragment(DashboardFragment())

        binding.bottomNavigationView.setOnItemSelectedListener{
             when(it.itemId) {
                 R.id.dashboard -> {
                     addNavLayoutSetup()
                     replaceFragment(dashboardFragment)
                 }
                 R.id.add -> {
                     replaceFragment(addFragment)
                 }
                 else ->{
                     addNavLayoutSetup()
                     replaceFragment(aboutFragment)
                 }
             }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    override fun onOpenNote(note: Note) {
        SharedPrefHelper.saveSelectedNote(note.id, this)
        replaceFragment(addFragment)
        binding.bottomNavigationView.menu.getItem(1).isChecked = true
        binding.bottomNavigationView.menu.findItem(R.id.add).setIcon(R.drawable.ic_baseline_change_circle_24)
        binding.bottomNavigationView.menu.findItem(R.id.add).title = "Update"
    }

    override fun onDashboardReturn() {
        binding.bottomNavigationView.menu.getItem(0).isChecked = true
        addNavLayoutSetup()
        replaceFragment(DashboardFragment())
    }

    private fun addNavLayoutSetup() {
        binding.bottomNavigationView.menu.findItem(R.id.add).setIcon(R.drawable.ic_baseline_add_circle_outline_24)
        binding.bottomNavigationView.menu.findItem(R.id.add).title = "Add"
    }

    override fun onBackPressed() {
        if (isTapDoubled) {
            super.onBackPressed()
        }
        if (!isTapDoubled) Toast.makeText(this,"Double tap to close", Toast.LENGTH_SHORT).show()

        isTapDoubled = true
        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                isTapDoubled = false
            }
        },2000)

    }
}