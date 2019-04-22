package com.example.mvvmkt

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }

    private var noteViewModel: NoteViewModel? = null
    private var noteToDelete: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAdd = findViewById<FloatingActionButton>(R.id.bt_add_note)
        buttonAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val adapter = NoteAdapter()
        recyclerView.adapter = adapter

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel?.getAllNotesFromDB()?.observe(this, Observer { notes ->
            notes?.let { adapter.submitList(it) }
        })

        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.description)
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.priority)
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.id)

                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteToDelete = adapter.getNoteAt(viewHolder.adapterPosition)
                noteViewModel?.delete(noteToDelete!!)
                val snackBar = Snackbar.make(findViewById(R.id.recycler_view), "Note deleted", Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.snack_bar_undo) {undoDelete()}
                snackBar.setActionTextColor(Color.YELLOW)
                snackBar.show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    private fun undoDelete() {
        noteToDelete?.let {
            noteViewModel?.insert(it)
        }
        noteToDelete = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title = data!!.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
            val priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

            val note = Note(null, title, description, priority)
            noteViewModel?.insert(note)

            val snackBar = Snackbar.make(findViewById(R.id.recycler_view), "Note Added", Snackbar.LENGTH_LONG)
            snackBar.show()

        }

        if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data!!.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()
                return
            }

            val title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
            val priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

            val note = Note(null, title, description, priority)
            note.id = id
            noteViewModel?.update(note)

            val snackBar = Snackbar.make(findViewById(R.id.recycler_view), "Note updated", Snackbar.LENGTH_LONG)
            snackBar.show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel?.deleteAllNotes()
                val snackBar =
                    Snackbar.make(findViewById(R.id.recycler_view), "All notes deleted", Snackbar.LENGTH_LONG)
                snackBar.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
