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

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ar.com.scacchipa.todolist.contentprovider.TaskContract
import com.example.android.todolist.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private var mPriority = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.radButton1.isChecked = true
        mPriority = 1
    }

    fun onClickAddTask(view: View?) {

        val input = binding.editTextTaskDescription.text.toString()
        if (input.isEmpty()) {
            return
        }

        val contentValues = ContentValues()

        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input)
        contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, mPriority)

        val uri = contentResolver.insert(TaskContract.TaskEntry.CONTENT_URI, contentValues)

        if (uri != null) {
            Toast.makeText(baseContext, uri.toString(), Toast.LENGTH_LONG).show()
        }

        finish()
    }

    fun onPrioritySelected(view: View?) {
        when {
            binding.radButton1.isChecked -> mPriority = 1
            binding.radButton2.isChecked -> mPriority = 2
            binding.radButton3.isChecked -> mPriority = 3
        }
    }
}