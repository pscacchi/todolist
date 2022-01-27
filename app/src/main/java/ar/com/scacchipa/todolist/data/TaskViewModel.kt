package ar.com.scacchipa.todolist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val myTaskRepository = TaskRepository(application.contentResolver)

    val taskList = liveData { emit(myTaskRepository.fetchTasks()) }
            as MutableLiveData<List<Task>>


    fun update(newTaskList: List<Task>) {
        this.taskList.value = newTaskList
    }
}