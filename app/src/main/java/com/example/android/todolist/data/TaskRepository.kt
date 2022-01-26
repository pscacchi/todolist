package com.example.android.todolist.data

import android.content.ContentResolver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(contentResolver: ContentResolver) {

    val source = TaskDataSource(contentResolver)
    suspend fun fetchTasks(): List<Task> = withContext(Dispatchers.IO) {
        source.fetchTasks()
    }
}