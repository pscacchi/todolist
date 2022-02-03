package ar.com.scacchipa.todolist.data

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    var app: Application? = null
        set(value) {
            field = value
            if (app != null) {
                myTaskRepository = TaskRepository(app!!.contentResolver)
                update()
            }
        }

    private var myTaskRepository:TaskRepository? = null

    val taskListModel = MutableLiveData<List<Task>>()

    fun update() {
        CoroutineScope(Dispatchers.IO).launch {
            if (myTaskRepository != null) {
                val taskList: List<Task> = myTaskRepository!!.fetchTasks()
                launch(Dispatchers.Main) {
                    taskListModel.value = taskList
                }
            }
        }
    }
    fun insert(title: String, priority: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            if (myTaskRepository != null) {
                val taskList: List<Task> = myTaskRepository!!.insert(title, priority)
                launch(Dispatchers.Main) {
                    taskListModel.value = taskList
                }
            }
        }
    }
    fun delete(pos: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            if (myTaskRepository != null) {
                val taskList = myTaskRepository!!.delete(pos)
                launch(Dispatchers.Main) {
                    taskListModel.value = taskList
                }
            }
        }
    }
}