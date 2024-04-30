package com.example.lr13

import java.io.Serializable

class Todo(val id: Int, val title: String, val status: TodoStatus): Serializable {
    enum class TodoStatus {
        COMPLETED,
        INCOMPLETE
    }
}
