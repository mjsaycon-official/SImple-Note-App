package com.example.simplenoteapp.repository

import androidx.lifecycle.LiveData
import com.example.simplenoteapp.data.entity.Note
import com.example.simplenoteapp.interfaces.NotesDao

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

    fun get(noteID: Int): LiveData<Note> {
        return notesDao.getNote(noteID)
    }
}