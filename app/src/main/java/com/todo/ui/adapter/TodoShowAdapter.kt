package com.todo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.todo.data.model.TodoListResponseItem
import com.todo.databinding.ItemTodoDetailsBinding
import com.todo.utils.TodoDiffutils



/**
 * Created by Mohi Uddin on 7/10/22
 */
class TodoShowAdapter : RecyclerView.Adapter<TodoShowAdapter.TodoShowHolder>() {
    private var oldTodoList = emptyList<TodoListResponseItem>()
    class TodoShowHolder (val binding: ItemTodoDetailsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoShowHolder {

        val binding = ItemTodoDetailsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return TodoShowHolder(
            binding
        )
    }


    override fun onBindViewHolder(holder: TodoShowHolder, position: Int) {
          holder.binding.tvTodoTitleHead.text = "Title: ${oldTodoList[position].title}"
          holder.binding.tvTodoStatus.text = "Status: ${oldTodoList[position].completed}"
    }

    override fun getItemCount(): Int {
       return oldTodoList.size
    }

    fun setUpdateData(newTodoList : List<TodoListResponseItem>){
        val diffUtil = TodoDiffutils(oldTodoList,newTodoList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        oldTodoList = newTodoList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}