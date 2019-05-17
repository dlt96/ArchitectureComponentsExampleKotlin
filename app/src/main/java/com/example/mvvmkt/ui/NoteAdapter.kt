package com.example.mvvmkt.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkt.R
import com.example.mvvmkt.data.db.entity.Note


class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteHolder>(DiffCallback()) {

    private var listener: OnItemClickListener? = null


    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.description.equals(newItem.description) &&
                    oldItem.title == newItem.title &&
                    oldItem.priority == newItem.priority
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
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

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.text_view_title)
        private val tvDescription: TextView = itemView.findViewById(R.id.text_view_description)
        private val tvPriority: TextView = itemView.findViewById(R.id.text_view_priority)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(getItem(position))
                }
            }
        }

        fun bind(note: Note) {
            tvTitle.text = note.title
            tvDescription.text = note.description
            tvPriority.text = note.priority.toString()
        }
    }
}