package com.example.notesusingmvvmandroom.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notesusingmvvmandroom.Model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)
    @Update
    suspend fun updateNote(note: Note)
    @Delete
    suspend fun deleteNote(note:Note)
    @Query("Select * from notes order by id ASC")
    fun getAllNotes():LiveData<List<Note>>
    @Query("Select * from notes where Title like :query OR notes Like :query ")
    fun searchNote(query:String?):LiveData<List<Note>>
    @Query("Select * from notes where id=:noteId")
    fun getNoteById(noteId: Int): LiveData<Note>



}