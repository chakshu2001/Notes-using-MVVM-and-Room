package com.example.notesusingmvvmandroom.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notesusingmvvmandroom.Database.noteDatabase
import com.example.notesusingmvvmandroom.Model.Note
import com.example.notesusingmvvmandroom.Repository.noteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application):AndroidViewModel (application){
    val db = noteDatabase.invoke(application)
    private val repository: noteRepository=noteRepository(db)
    val allNotes: LiveData<List<Note>> =repository.getAllNotes()
    fun insert(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(note)
        }
    }
    fun delete(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
        Toast.makeText(getApplication(),"Note get Deleted",Toast.LENGTH_SHORT).show()
    }

    // Update a note
    fun update(note: Note) {
        viewModelScope.launch {
            repository.update(note)
        }
        Toast.makeText(getApplication(), "Note Updated", Toast.LENGTH_SHORT).show()
    }

    // Search for notes
    fun searchNote(query: String?): LiveData<List<Note>> {
        return repository.searchNote(query)
    }
    fun getNoteById(noteId: Int): LiveData<Note> {
        return repository.getNoteById(noteId) // Fetch the note by ID from the repository
    }

}