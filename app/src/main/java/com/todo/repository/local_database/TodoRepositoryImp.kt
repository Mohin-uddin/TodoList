package com.todo.repository.local_database

import com.todo.data.data_source.TodoDao
import com.todo.data.model.TodoListResponseItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Mohi Uddin on 7/10/22
 */
class TodoRepositoryImp @Inject constructor(
    private val dao: TodoDao
) : TodoRepository {
    override fun getAllTodo(): Flow<List<TodoListResponseItem>> {
        return dao.getAllTodo()
    }

    override suspend fun insertDao(todoListResponseItem: TodoListResponseItem) {
        return dao.insertDao(todoListResponseItem)
    }
}