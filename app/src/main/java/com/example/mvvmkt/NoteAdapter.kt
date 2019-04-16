package com.example.mvvmkt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteHolder>(DiffCallback()) {

    private var listener: OnItemClickListener? = null


    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.description.equals(newItem.description) &&
                    oldItem.title.equals(newItem.title) &&
                    oldItem.priority == newItem.priority
        }

    }

    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.text_view_title)
        val tvDescription = itemView.findViewById<TextView>(R.id.text_view_description)
        val tvPriority = itemView.findViewById<TextView>(R.id.text_view_priority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.itemView.setOnClickListener {
            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener!!.onItemClick(getItem(position))
            }
        }

        val note = getItem(position)
        holder.tvTitle.text = note.title
        holder.tvDescription.text = note.description
        holder.tvPriority.text = note.priority.toString()

    }

    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}