package com.example.mvvmkt

import androidx.lifecycle.LiveData

interface NoteRepositoryContractor {

    fun insert(note: Note)

    fun update(note: Note)

    fun delete(note: Note)

    fun deleteAllNotes()

    fun getAllNotes(): LiveData<List<Note>>?
}