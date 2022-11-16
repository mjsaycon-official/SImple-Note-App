package com.example.simplenoteapp.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.simplenoteapp.entity.Note

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notesTable WHERE ID=:noteID")
    fun getNote(noteID: Int): LiveData<Note>

    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}