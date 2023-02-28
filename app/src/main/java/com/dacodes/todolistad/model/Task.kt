package com.dacodes.todolistad.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val task: String,
    val priority: Int,
    var completed: Boolean
)
