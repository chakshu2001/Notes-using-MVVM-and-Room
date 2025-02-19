package com.example.notesusingmvvmandroom.Model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name = "notes")
    val notes:String
):Parcelable{

}
