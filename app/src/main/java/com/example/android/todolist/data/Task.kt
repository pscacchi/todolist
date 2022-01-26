package com.example.android.todolist.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Task( val id: Int, val description: String, val priority: Int )

class TaskViewModel : ViewModel() {
    val taskList = MutableLiveData<List<Task>>()

    fun update(newTaskList: List<Task>) {
        this.taskList.value = newTaskList
    }

}