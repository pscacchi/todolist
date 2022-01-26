package com.example.android.todolist.data

import android.content.ContentResolver

class TaskDataSource(
    private val contentResolver: ContentResolver
) {
    fun fetchTasks() :List<Task> {
        val result = mutableListOf<Task>()

        val cursor = contentResolver.query(TaskContract.TaskEntry.CONTENT_URI, null,
            null , null, TaskContract.TaskEntry.COLUMN_PRIORITY)

        cursor?.let {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val idIndex = it.getColumnIndex(TaskContract.TaskEntry._ID)
                val descriptionIndex = it.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION)
                val priorityIndex = it.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY)
                result.add(
                    Task(
                        cursor.getInt(idIndex),
                        cursor.getString(descriptionIndex),
                        cursor.getInt(priorityIndex)
                    )
                )
                it.moveToNext()
            }
            cursor.close()
        }
        return result.toList()
    }
}