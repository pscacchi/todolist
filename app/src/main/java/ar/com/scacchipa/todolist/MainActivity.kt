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

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.scacchipa.todolist.contentprovider.TaskContract
import ar.com.scacchipa.todolist.data.TaskViewModel
import com.example.android.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var tasksVM: TaskViewModel
    private lateinit var binding: ActivityMainBinding

    private var mAdapter: CustomCursorAdapter? = null
    private var mRecyclerView: RecyclerView? = null

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val TASK_LOADER_ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        mAdapter = CustomCursorAdapter(this)
        binding.recyclerViewTasks.adapter = mAdapter

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                val id = viewHolder.itemView.tag as Int
                val stringId = id.toString()
                var uri = TaskContract.TaskEntry.CONTENT_URI
                    .buildUpon().appendPath(stringId).build()

                contentResolver.delete(uri, null, null)
            }
        }).attachToRecyclerView(binding.recyclerViewTasks)

        binding.floatingActionButton.setOnClickListener {
            val addTaskIntent = Intent(baseContext, AddTaskActivity::class.java)
            startActivity(addTaskIntent)
        }

        tasksVM = ViewModelProvider.AndroidViewModelFactory(application!!)
            .create(TaskViewModel::class.java)

        tasksVM.taskList.observe(  this, {
            Log.i("INFO","task list changed")
            mAdapter!!.swapCursor(tasksVM.taskList.value)
        })
    }
}
