package com.example.mvvmkt

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class NoteViewModel(application: Application): AndroidViewModel(application) {

    val repository = NoteRepository(application)
    val allNotes = repository.getAllNotes()

    fun insert(note: Note) = repository.insert(note)

    fun update(note: Note) = repository.update(note)

    fun delete(note: Note) = repository.delete(note)

    fun deleteAllNotes() = repository.deleteAllNotes()

    fun getAllNotesFromDB() = allNotes

}