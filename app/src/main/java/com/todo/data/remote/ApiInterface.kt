package com.todo.data.remote

import com.todo.data.model.TodoListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Mohi Uddin on 7/10/22
 */
interface ApiInterface {
    @GET("todos")
    suspend fun getTodoList(
    ): TodoListResponse
}