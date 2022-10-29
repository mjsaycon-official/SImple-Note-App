package com.example.simplenotesapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenotesapplication.R
import com.example.simplenotesapplication.entity.Note

class NoteAdapter(
    val context: Context,
    val noteDeleteInterface: NoteDeleteInterface,
    val noteOpenInterface: NoteOpenInterface) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val allNotes = ArrayList<Note>()
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val tvTitle = itemView.findViewById<TextView>(R.id.idTVTitle)
            val tvTimestamp = itemView.findViewById<TextView>(R.id.idTVTimestamp)
            val ivDelete = itemView.findViewById<ImageView>(R.id.idIVDelete)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.setText(allNotes.get(position).title)
        holder.tvTimestamp.setText(allNotes.get(position).timestamp)

        holder.ivDelete.setOnClickListener {
            noteDeleteInterface.onDeleteClick(allNotes.get(position))
        }

        holder.itemView.setOnClickListener {
            noteOpenInterface.onOpenNote(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface NoteDeleteInterface {
    fun onDeleteClick(note: Note)
}

interface NoteOpenInterface {
    fun onOpenNote(note: Note)
}