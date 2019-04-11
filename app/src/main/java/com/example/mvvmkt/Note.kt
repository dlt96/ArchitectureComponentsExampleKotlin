package com.example.mvvmkt

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note (
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val title: String,
    val description: String?,
    val priority: Int?
)