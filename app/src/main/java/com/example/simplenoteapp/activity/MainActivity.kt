package com.example.simplenoteapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenoteapp.R
import com.example.simplenoteapp.adapter.NoteAdapter
import com.example.simplenoteapp.adapter.NoteDeleteInterface
import com.example.simplenoteapp.adapter.NoteOpenInterface
import com.example.simplenoteapp.databinding.ActivityMainBinding
import com.example.simplenoteapp.entity.Note
import com.example.simplenoteapp.fragment.AboutFragment
import com.example.simplenoteapp.fragment.AddFragment
import com.example.simplenoteapp.fragment.DashboardFragment
import com.example.simplenoteapp.fragment.ReturnDashboardInterface
import com.example.simplenoteapp.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteDeleteInterface, NoteOpenInterface, ReturnDashboardInterface {

    lateinit var binding: ActivityMainBinding
    val TAG = "MainActivity"

    var notes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(DashboardFragment())

        binding.bottomNavigationView.setOnItemSelectedListener{
             when(it.itemId) {
                 R.id.dashboard -> {
                     replaceFragment(DashboardFragment())
                     Log.i(TAG, "onCreate: DashboardFragment")
                 }
                 R.id.add -> {
                     replaceFragment(AddFragment())
                     Log.i(TAG, "onCreate: AddFragment")
                 }
                 else ->{
                     replaceFragment(AboutFragment())
                     Log.i(TAG, "onCreate: AboutFragment")
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

    override fun onDeleteClick(note: Note) {

    }

    override fun onOpenNote(note: Note) {
        val intent = Intent(this, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteDescription", note.description)
        intent.putExtra("noteTimestamp", note.timestamp)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }

    override fun onDashboardReturn() {
        binding.bottomNavigationView.menu.getItem(0).setChecked(true)
        replaceFragment(DashboardFragment())
        Log.i(TAG, "onCreate: DashboardFragment")
    }
}