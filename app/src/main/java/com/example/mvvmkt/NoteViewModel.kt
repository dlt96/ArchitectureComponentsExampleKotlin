package com.example.mvvmkt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    val snackbar: LiveData<String>
        get() = _snackBar

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get() = _spinner

    fun insert(note: Note) =
        launchDataLoad{ repository.insert(note) }

    fun update(note: Note) =
        launchDataLoad{ repository.update(note) }

    fun delete(note: Note) =
        launchDataLoad{ repository.delete(note) }

    fun deleteAllNotes() {
        launchDataLoad{ repository.deleteAllNotes() }
    }

    fun getAllNotesFromDB() = allNotes

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                delay(500)
                block()
            } catch (error: NoteRepository.TitleRefreshError) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }

    fun onSnackbarShown() {
        _snackBar.value = null
    }

}