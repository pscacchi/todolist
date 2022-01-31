package ar.com.scacchipa.mylibrary.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(val app: Application) : AndroidViewModel(app) {

    private val myTaskRepository = TaskRepository(app.contentResolver)

    val taskList = liveData { emit(myTaskRepository.fetchTasks()) }
            as MutableLiveData<List<Task>>

    fun update(newTaskList: List<Task>) {
        this.taskList.value = newTaskList
    }
    fun update() {
        CoroutineScope(Dispatchers.Main).launch {
            taskList.value = myTaskRepository.fetchTasks()
        }
    }
    fun insert(title: String, priority: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            taskList.value = myTaskRepository.insert(title, priority)
        }
    }
    fun delete(pos: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            taskList.value = myTaskRepository.delete(pos)
        }
    }
}