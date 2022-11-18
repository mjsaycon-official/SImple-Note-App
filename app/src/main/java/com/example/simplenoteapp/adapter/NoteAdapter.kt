package com.example.simplenoteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenoteapp.R
import com.example.simplenoteapp.entity.Note
import com.example.simplenoteapp.fragment.DashboardFragment

class NoteAdapter(
    val context: DashboardFragment,
    private val noteDeleteInterface: NoteDeleteInterface,
    private val noteOpenInterface: NoteOpenInterface) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

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
        holder.tvTitle.text = allNotes[position].title
        holder.tvTimestamp.text = allNotes[position].timestamp

        holder.ivDelete.setOnClickListener {
            noteDeleteInterface.onDeleteClick(allNotes[position])
        }

        holder.itemView.setOnClickListener {
            noteOpenInterface.onOpenNote(allNotes[position])
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