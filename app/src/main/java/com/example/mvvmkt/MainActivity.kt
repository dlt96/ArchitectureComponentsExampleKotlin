package com.example.mvvmkt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    companion object {
        @JvmField val ADD_NOTE_REQUEST = 1
        @JvmField val EDIT_NOTE_REQUEST = 2//to not make getter,setter
    }

    private var noteViewModel: NoteViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val adapter = NoteAdapter()
        recyclerView.adapter = adapter

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel?.getAllNotesFromDB()?.observe(this, Observer { notes ->
            notes?.let { adapter.submitList(it)}
        })

        noteViewModel?.insert(Note(0, "hola", "desc", 1))

        adapter.setOnItemClickListener( object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                TODO()
            }
        })
    }


}
