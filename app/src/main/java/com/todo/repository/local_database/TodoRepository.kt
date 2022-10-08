package com.todo.repository.local_database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todo.data.model.TodoListResponseItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mohi Uddin on 7/10/22
 */
interface TodoRepository {
    fun getAllTodo(): Flow<List<TodoListResponseItem>>
    suspend fun insertDao(todoListResponseItem: TodoListResponseItem)
}