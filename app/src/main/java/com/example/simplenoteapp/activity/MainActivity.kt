package com.example.simplenotesapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenotesapplication.R
import com.example.simplenotesapplication.adapter.NoteAdapter
import com.example.simplenotesapplication.adapter.NoteDeleteInterface
import com.example.simplenotesapplication.adapter.NoteOpenInterface
import com.example.simplenotesapplication.entity.Note
import com.example.simplenotesapplication.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteDeleteInterface, NoteOpenInterface {

    lateinit var addFAb: FloatingActionButton
    lateinit var noteRV: RecyclerView
    lateinit var noteViewModel: NoteViewModel

    var notes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFAb = findViewById(R.id.idFABHome)
        noteRV = findViewById(R.id.idNotesRV)

        noteRV.layoutManager = LinearLayoutManager(this)

        val noteAdapter = NoteAdapter(this, this, this)
        noteRV.adapter = noteAdapter
        noteViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        noteViewModel.allNote.observe(this, { list->
            list?.let {
                noteAdapter.updateList(it)
            }
        })
        addFAb.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDeleteClick(note: Note) {
        noteViewModel.deleteNote(note)
        Toast.makeText(this, "${note.title} Deleted!", Toast.LENGTH_SHORT).show()
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
}