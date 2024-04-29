package com.example.lr13

class Todo(val id: Int, val title: String, val status: TodoStatus) {
    enum class TodoStatus {
        COMPLETED,
        INCOMPLETE
    }
}
