package com.example.todolist

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : Activity() {
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoInput: EditText
    private var todoIdCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoInput = findViewById(R.id.todoInput)
        setupRecyclerView()
        setupAddButton()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.todoRecyclerView)
        todoAdapter = TodoAdapter(
            mutableListOf(),
            onTodoCheckedChange = { todo ->
                val message = if (todo.isCompleted) {
                    "Task marked as completed"
                } else {
                    "Task marked as incomplete"
                }
                showToast(message)
            },
            onTodoLongClick = { todo ->
                showDeleteConfirmationDialog(todo)
                true
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }
    }

    private fun setupAddButton() {
        findViewById<Button>(R.id.addButton).setOnClickListener {
            val todoText = todoInput.text.toString().trim()
            if (todoText.isNotEmpty()) {
                val todo = TodoItem(id = todoIdCounter++, title = todoText)
                todoAdapter.addTodo(todo)
                todoInput.text.clear()
                showToast("Task added")
            } else {
                showToast("Please enter a task")
            }
        }
    }

    private fun showDeleteConfirmationDialog(todo: TodoItem) {
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete") { _, _ ->
                todoAdapter.removeTodo(todo)
                showToast("Task deleted")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}