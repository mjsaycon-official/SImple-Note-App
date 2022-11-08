package com.example.simplenoteapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.simplenoteapp.data.NoteDatabase
import com.example.simplenoteapp.entity.Note
import com.example.simplenoteapp.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val allNote: LiveData<List<Note>>
    val repository: NotesRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NotesRepository(dao)
        allNote = repository.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}