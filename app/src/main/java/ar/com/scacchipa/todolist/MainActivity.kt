/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package ar.com.scacchipa.todolist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ar.com.scacchipa.todolist.data.IFragManager
import ar.com.scacchipa.todolist.data.TaskViewModel
import com.example.android.todolist.R
import com.example.android.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), IFragManager {

    private lateinit var binding: ActivityMainBinding
    private val tasksVM: TaskViewModel by viewModels()
    private lateinit var taskListFragment: TaskListFragment
    private lateinit var addTaskFragment: AddTaskFragment

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val TASK_LOADER_ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        tasksVM.app = application
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.initFragments()

    }

    /**
     * IFragFragment implementation
     */

    override fun initFragments() {
        taskListFragment = TaskListFragment()
        addTaskFragment = AddTaskFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, taskListFragment)
            .commit()
    }

    override fun showListFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, taskListFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun showAddingFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, addTaskFragment)
            .addToBackStack(null)
            .commit()
    }
}
