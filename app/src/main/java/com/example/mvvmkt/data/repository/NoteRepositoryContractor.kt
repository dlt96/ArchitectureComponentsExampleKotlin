package com.example.mvvmkt.data.repository

import androidx.lifecycle.LiveData
import com.example.mvvmkt.data.db.entity.Note

interface NoteRepositoryContractor {

    suspend fun insert(note: Note)

    suspend fun update(note: Note)

    suspend fun delete(note: Note)

    suspend fun deleteAllNotes()

    fun getAllNotes(): LiveData<List<Note>>?
}