package com.todo.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todo.data.model.TodoListResponseItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mohi Uddin on 7/10/22
 */
@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_list")
    fun getAllTodo(): Flow<List<TodoListResponseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDao(todoListResponseItem: TodoListResponseItem)
}