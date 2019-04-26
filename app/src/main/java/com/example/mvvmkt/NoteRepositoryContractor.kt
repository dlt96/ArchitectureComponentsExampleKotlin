package com.example.mvvmkt

import androidx.lifecycle.LiveData

interface NoteRepositoryContractor {

    suspend fun insert(note: Note)

    suspend fun update(note: Note)

    suspend fun delete(note: Note)

    suspend fun deleteAllNotes()

    fun getAllNotes(): LiveData<List<Note>>?
}