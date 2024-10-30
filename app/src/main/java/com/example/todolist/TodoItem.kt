package com.example.todolist

data class TodoItem(
    val id: Int,
    var title: String,
    var isCompleted: Boolean = false
)