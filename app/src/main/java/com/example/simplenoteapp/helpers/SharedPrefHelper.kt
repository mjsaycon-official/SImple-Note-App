package com.example.simplenoteapp.helpers

import android.content.Context
import android.content.SharedPreferences
import com.example.simplenoteapp.R

class SharedPrefHelper {
    companion object {
        lateinit var sharedPref: SharedPreferences
        lateinit var sharedPrefEditor: SharedPreferences.Editor

        fun initialize(context: Context) {
            sharedPref = context.getSharedPreferences(context.resources.getString(R.string.notes_shared_pref), Context.MODE_PRIVATE)
            sharedPrefEditor = sharedPref.edit()
        }

        fun saveSelectedNote(noteId: Int, context: Context) {
            sharedPrefEditor.putInt(context.resources.getString(R.string.note_id),noteId)
            sharedPrefEditor.apply()
        }

        fun getSelectedNote(context: Context): Int {
            return sharedPref.getInt(context.resources.getString(R.string.note_id),-1)
        }

        fun clearSelectedNote() {
            sharedPrefEditor.clear()
            sharedPrefEditor.apply()
        }
    }

}