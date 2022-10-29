package com.example.simplenotesapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.simplenotesapplication.R
import com.example.simplenotesapplication.entity.Note
import com.example.simplenotesapplication.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var etTitle: EditText
    lateinit var etDesc: EditText
    lateinit var btnSave: Button
    lateinit var noteViewModel: NoteViewModel

    var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        etTitle = findViewById(R.id.idETTitle)
        etDesc = findViewById(R.id.idETDesc)
        btnSave = findViewById(R.id.idBtnSave)

        noteViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")

        if(noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("note")
            val noteDescription = intent.getStringExtra("noteDescription")
            val noteTimestamp = intent.getStringExtra("noteTimestamp")
            noteID = intent.getIntExtra("noteID", -1)
            etTitle.setText(noteTitle)
            etDesc.setText(noteDescription)
        } else {


        }

        btnSave.setOnClickListener {
            val currentTimeStamp: String = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(Date())
            val note = Note(etTitle.text.toString(), etDesc.text.toString(), currentTimeStamp)
            noteViewModel.addNote(note)
            finish()
        }

        etTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
    }
}