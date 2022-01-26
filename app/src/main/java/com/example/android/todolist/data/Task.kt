package com.example.android.todolist.data

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

data class Task( val id: Int, val description: String, val priority: String )

class TaskViewModel : ViewModel() {
    private val taskList = MutableLiveData<List<Task>>()

    fun update(newTaskList: List<Task>) {
        this.taskList.value = newTaskList
    }
}