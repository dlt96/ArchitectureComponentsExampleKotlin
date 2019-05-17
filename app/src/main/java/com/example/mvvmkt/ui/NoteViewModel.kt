package com.example.mvvmkt.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.mvvmkt.data.db.entity.Note
import com.example.mvvmkt.data.db.NoteDatabase
import com.example.mvvmkt.data.repository.NoteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    private val allNotes: LiveData<List<Note>>

    init {
        val noteDao  = NoteDatabase.getInstance(application).noteDao()
        repository = NoteRepository(noteDao = noteDao)
        allNotes = repository.getAllNotes()
    }

    private val _snackBar = MutableLiveData<String>()
    val snackbar: LiveData<String> = Transformations.map(_snackBar) {snackbar -> snackbar}

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = Transformations.map(_spinner) { show -> show }

    fun insert(note: Note) {
        launchDataLoad({ repository.insert(note) },"Note added" )
    }

    fun update(note: Note) {
        launchDataLoad({ repository.update(note) }, "Note edited")
    }

    fun delete(note: Note) {
        launchDataLoad({ repository.delete(note) }, "Note deleted")
    }


    fun deleteAllNotes() {
        launchDataLoad( { repository.deleteAllNotes() }, "All notes deleted")
    }

    fun getAllNotesFromDB() = allNotes

    private fun launchDataLoad(block: suspend () -> Unit, snackbarTitle: String): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: NoteRepository.TitleRefreshError) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
                _snackBar.value = snackbarTitle
            }
        }
    }

    fun onSnackbarShown() {
        _snackBar.value = null
    }

}