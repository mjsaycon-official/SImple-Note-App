package com.example.simplenoteapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
class Note (
    @ColumnInfo(name = "Title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "color") val color: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}