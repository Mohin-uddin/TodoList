package com.todo.repository.api

import com.todo.data.model.TodoListResponse
import com.todo.utils.Resource

/**
 * Created by Mohi Uddin on 7/10/22
 */
interface TodoListRepository {
    suspend fun todoListResponse():
            Resource<TodoListResponse>
}