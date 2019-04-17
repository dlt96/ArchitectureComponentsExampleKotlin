package com.example.mvvmkt

import android.app.Application
import org.jetbrains.anko.doAsync

class NoteRepository (
    application: Application
): NoteRepositoryContractor {

    private val noteDao = NoteDatabase.getInstance(application)?.noteDao()
    private val allNotes = noteDao?.getAllNotes()

    override fun insert(note: Note) {
        doAsync { noteDao?.insert(note) }
    }

    override fun update(note: Note) {
        doAsync { noteDao?.update(note) }
    }

    override fun delete(note: Note) {
        doAsync { noteDao?.delete(note) }
    }

    override fun deleteAllNotes() {
        doAsync {
            noteDao?.deleteAllNotes()
        }
    }

    override fun getAllNotes() = allNotes
    //Room automatically executes this in a BG thread.
}