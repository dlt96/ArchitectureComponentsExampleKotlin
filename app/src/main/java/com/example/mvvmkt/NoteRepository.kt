package com.example.mvvmkt

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.doAsync

class NoteRepository(
    private val noteDao: NoteDao
) : NoteRepositoryContractor {

    private val allNotes = noteDao.getAllNotes()

    override suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) { noteDao.insert(note) }
    }

    override suspend fun update(note: Note) {
        withContext(Dispatchers.IO) { noteDao.update(note) }
    }

    override suspend fun delete(note: Note) {
        withContext(Dispatchers.IO) { noteDao.delete(note) }
    }

    override suspend fun deleteAllNotes() {
        withContext(Dispatchers.IO) { noteDao.deleteAllNotes() }
    }

    override fun getAllNotes() = allNotes
    //Room automatically executes this in a BG thread.

    class TitleRefreshError(cause: Throwable) : Throwable(cause.message, cause)
}