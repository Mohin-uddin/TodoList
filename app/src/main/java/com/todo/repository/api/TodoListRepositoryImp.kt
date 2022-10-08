package com.todo.repository.api

import android.util.Log
import com.todo.data.model.TodoListResponse
import com.todo.data.remote.ApiInterface
import com.todo.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Mohi Uddin on 7/10/22
 */
class TodoListRepositoryImp @Inject constructor(
    private val api: ApiInterface
) : TodoListRepository {
    override suspend fun todoListResponse(): Resource<TodoListResponse> {
        val response = try {
            api.getTodoList()
        }catch (ioExeption: IOException){
            Log.e("CheckCall", "shop Response: $ioExeption" )
            return Resource.Error(ioExeption.toString())

        }
        catch (e: HttpException){
            Log.e("CheckCall", "shopres hlw: "+e.response().toString()+"  asdad "+e.code())
            return Resource.Error(""+e.response()?.message())
        }
        Log.e("CheckCall", "shop: ")
        return Resource.Success(response)
    }
}