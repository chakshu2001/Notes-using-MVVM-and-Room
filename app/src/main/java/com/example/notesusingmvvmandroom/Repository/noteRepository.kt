package com.example.notesusingmvvmandroom.Repository

import androidx.lifecycle.LiveData
import com.example.notesusingmvvmandroom.Database.noteDatabase
import com.example.notesusingmvvmandroom.Model.Note

class noteRepository(private  val db:noteDatabase) {
    suspend fun insert(note: Note){
        db.getNoteDao().insert(note)
    }
    suspend fun delete(note: Note){
        db.getNoteDao().deleteNote(note)
    }
    suspend fun update(note: Note){
        db.getNoteDao().updateNote(note)
    }
     fun getAllNotes(): LiveData<List<Note>> {
        return db.getNoteDao().getAllNotes()
    }
    fun searchNote(query: String?): LiveData<List<Note>> {
        return db.getNoteDao().searchNote(query)
    }
    fun getNoteById(query: Int): LiveData<Note> {
        return db.getNoteDao().getNoteById(query)
    }
}
