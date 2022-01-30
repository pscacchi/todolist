package ar.com.scacchipa.todolist.data

import android.content.ContentResolver

class TaskRepository(contentResolver: ContentResolver) {

    val source = TaskDataSource(contentResolver)

    fun fetchTasks(): List<Task> =
        source.fetchTasks()


    fun insert(title: String, priority: Int):List<Task> {
            source.insert(title, priority)
            return source.fetchTasks()
        }

    fun delete(pos: Int):List<Task> {
            source.delete(pos)
            return source.fetchTasks()
        }
}