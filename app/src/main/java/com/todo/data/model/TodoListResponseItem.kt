package com.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "todo_list")
data class TodoListResponseItem(
    val completed: Boolean,
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val title: String,
    val userId: Int
)