package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private var todos: MutableList<TodoItem>,
    private val onTodoCheckedChange: (TodoItem) -> Unit,
    private val onTodoLongClick: (TodoItem) -> Boolean
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.todoTitle)
        val completedCheckBox: CheckBox = view.findViewById(R.id.todoCheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        holder.titleText.text = todo.title
        holder.completedCheckBox.isChecked = todo.isCompleted

        holder.completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            todo.isCompleted = isChecked
            onTodoCheckedChange(todo)
        }

        holder.itemView.setOnLongClickListener {
            onTodoLongClick(todo)
        }
    }

    override fun getItemCount() = todos.size

    fun addTodo(todo: TodoItem) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun removeTodo(todo: TodoItem) {
        val position = todos.indexOf(todo)
        if (position != -1) {
            todos.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}