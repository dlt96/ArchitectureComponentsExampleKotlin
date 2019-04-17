package com.example.mvvmkt

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note (
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var title: String,
    var description: String?,
    var priority: Int?
)