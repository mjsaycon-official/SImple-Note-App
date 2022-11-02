package com.example.simplenotesapplication.repository

import androidx.lifecycle.LiveData
import com.example.simplenotesapplication.entity.Note
import com.example.simplenotesapplication.interfaces.NotesDao

class NotesRepository(private val notesDao: NotesDao) {
    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }

    suspend fun delete(note: Note) {
        notesDao.delete(note)
    }

    suspend fun update(note: Note) {
        notesDao.update(note)
    }


}