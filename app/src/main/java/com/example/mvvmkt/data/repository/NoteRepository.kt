package com.example.mvvmkt.data.repository

import com.example.mvvmkt.data.db.entity.Note
import com.example.mvvmkt.data.db.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class NoteRepository(
    private val noteDao: NoteDao
) : NoteRepositoryContractor {

    private val allNotes = noteDao.getAllNotes()

    override suspend fun insert(note: Note) {
        delay(1000)
        withContext(Dispatchers.IO) { noteDao.insert(note) }
    }

    override suspend fun update(note: Note) {
        delay(1000)
        withContext(Dispatchers.IO) { noteDao.update(note) }
    }

    override suspend fun delete(note: Note) {
        withContext(Dispatchers.IO) {
            delay(1200)
            noteDao.delete(note) }
    }

    override suspend fun deleteAllNotes() {
        delay(1500)
        withContext(Dispatchers.IO) { noteDao.deleteAllNotes() }
    }

    override fun getAllNotes() = allNotes
    //Room automatically executes this in a BG thread.

    class TitleRefreshError(cause: Throwable) : Throwable(cause.message, cause)
}