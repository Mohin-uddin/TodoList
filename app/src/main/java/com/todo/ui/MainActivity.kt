package com.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.todo.databinding.ActivityMainBinding
import com.todo.ui.adapter.TodoShowAdapter
import com.todo.ui.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var todoShowAdapter: TodoShowAdapter
    lateinit var binding : ActivityMainBinding
    private  val todoViewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel.getTodoList()
        adapterInitialize()
        localDataGet()
    }

    private fun localDataGet() {
        lifecycleScope.launchWhenCreated {
            todoViewModel.todoListLocalData.collectLatest {
                if(it==null)
                    getApiTodoList()
                else{
                    todoViewModel.getTodoList()

                    lifecycleScope.launchWhenCreated {
                        todoViewModel.todoListLocalData.collectLatest {
                           it?.let {  localTodoList->
                               todoShowAdapter.setUpdateData(localTodoList)
                           }
                        }
                    }
                }
            }
        }
    }

    private fun getApiTodoList() {
        todoViewModel.getTodoApiCallResponse()
        lifecycleScope.launchWhenCreated {
            todoViewModel.todoListResponse.collectLatest {
                it?.let { todoList ->
                    todoShowAdapter.setUpdateData(todoList)
                    for (item in todoList){
                        Log.e("insertData", "getApiTodoList: ${item.title}", )
                       todoViewModel.insertTodo(item)
                    }
                }
            }
        }
    }


    private fun adapterInitialize() {
        todoShowAdapter = TodoShowAdapter()
        binding.rvTodoList.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvTodoList.adapter = todoShowAdapter

    }
}