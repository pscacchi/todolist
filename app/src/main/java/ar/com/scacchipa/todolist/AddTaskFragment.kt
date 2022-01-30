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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ar.com.scacchipa.todolist.data.IFragManager
import ar.com.scacchipa.todolist.data.TaskViewModel
import com.example.android.todolist.databinding.ActivityAddTaskBinding

class AddTaskFragment(
    private val tasksVM: TaskViewModel,
    private val callback: IFragManager) : Fragment() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        binding.addButton.setOnClickListener { onClickAddTask(view) }
        binding.radButton1.isChecked = true

        return binding.root
    }

    private fun onClickAddTask(view: View?) {

        val input = binding.editTextTaskDescription.text.toString()
        if (input.isEmpty()) {
            return
        }

        val priority = when {
            binding.radButton1.isChecked -> 1
            binding.radButton2.isChecked -> 2
            binding.radButton3.isChecked -> 3
            else -> 1
        }

        tasksVM.insert(input, priority);

        callback.showListFragment()
    }
}