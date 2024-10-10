package com.example.notesusingmvvmandroom.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesusingmvvmandroom.Model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class noteDatabase:RoomDatabase() {
    abstract fun getNoteDao():NoteDao
    companion object{
        @Volatile
        private var instance:noteDatabase?=null
        private val LOCK=Any()

        operator fun invoke(context:Context):noteDatabase {
            return instance ?: synchronized(LOCK)
            {
                instance ?: createDatabase(context).also {
                    instance = it

                }
            }

        }

        private fun createDatabase(context: Context): noteDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                noteDatabase::class.java,
                "app_database"
            ).build()
        }


    }
}