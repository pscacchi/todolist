package ar.com.scacchipa.todolist.manager.data

import android.content.ContentResolver
import android.content.ContentValues
import ar.com.scacchipa.todolist.manager.contentprovider.TaskContract

class TaskDataSource(
    private val contentResolver: ContentResolver
) {
    fun fetchTasks() :List<Task> {
        val result = mutableListOf<Task>()

        val cursor = contentResolver.query(
            TaskContract.TaskEntry.CONTENT_URI, null,
            null , null, TaskContract.TaskEntry.COLUMN_PRIORITY
        )

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

    fun insert(title: String, priority: Int) {
        val contentValues = ContentValues()

        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, title)
        contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, priority)

        val uri = contentResolver.insert(TaskContract.TaskEntry.CONTENT_URI, contentValues)

    }
    fun delete(pos: Int) {
        val uri = TaskContract.TaskEntry.CONTENT_URI
            .buildUpon().appendPath(pos.toString()).build()

        contentResolver.delete(uri, null, null)
    }
}