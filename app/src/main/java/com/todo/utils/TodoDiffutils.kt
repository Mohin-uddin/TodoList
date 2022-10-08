package com.todo.utils

import androidx.recyclerview.widget.DiffUtil
import com.todo.data.model.TodoListResponseItem

/**
 * Created by Mohi Uddin on 7/10/22
 */
class TodoDiffutils (private val oldTodoItem : List<TodoListResponseItem>,
                     private val newTodoItem : List<TodoListResponseItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldTodoItem.size
    }

    override fun getNewListSize(): Int {
        return newTodoItem.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldTodoItem[oldItemPosition].id==newTodoItem[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTodoItem==newTodoItem
    }
}