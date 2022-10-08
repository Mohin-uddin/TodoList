package com.todo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.data.model.TodoListResponse
import com.todo.data.model.TodoListResponseItem
import com.todo.repository.api.TodoListRepository
import com.todo.repository.local_database.TodoRepository
import com.todo.utils.DispatcherProvider
import com.todo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohi Uddin on 7/10/22
 */

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository: TodoListRepository,
    private val todoDataBaseRepository: TodoRepository,
    private var dispatcher: DispatcherProvider
): ViewModel() {
    private var _todoListResponse = MutableStateFlow<TodoListResponse?>(null)
    var todoListResponse: StateFlow<TodoListResponse?> = _todoListResponse.asStateFlow()

    private var _todoListLocalData = MutableStateFlow<List<TodoListResponseItem>?>(null)
    var todoListLocalData: StateFlow<List<TodoListResponseItem>?> = _todoListLocalData.asStateFlow()

    private var _responseCode = MutableStateFlow<String>("0")
    var responseCode: StateFlow<String> =_responseCode.asStateFlow()

    fun getTodoApiCallResponse(){
        viewModelScope.launch(dispatcher.io) {
            val result = todoRepository.todoListResponse()

            when(result){
                is Resource.Success -> {
                    _todoListResponse.emit(result.data)
                }
                is Resource.Error -> {
                    _responseCode.emit(result.message.toString())
                }
            }
        }
    }

    fun insertTodo(todoListResponseItem: TodoListResponseItem){
        viewModelScope.launch(dispatcher.io){
            todoDataBaseRepository.insertDao(todoListResponseItem)
        }

    }

    fun getTodoList(){
        viewModelScope.launch(dispatcher.io){
            todoDataBaseRepository.getAllTodo().collectLatest {
                _todoListLocalData.emit(it)
            }
        }
    }
}