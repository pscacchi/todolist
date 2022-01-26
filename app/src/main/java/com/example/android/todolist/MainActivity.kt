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
package com.example.android.todolist

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.todolist.data.Task
import com.example.android.todolist.data.TaskContract
import com.example.android.todolist.data.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private val tasksVM: TaskViewModel by viewModels()

    private var mAdapter: CustomCursorAdapter? = null
    private var mRecyclerView: RecyclerView? = null

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val TASK_LOADER_ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById<View>(R.id.recyclerViewTasks) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mAdapter = CustomCursorAdapter(this)
        mRecyclerView!!.adapter = mAdapter
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                val id = viewHolder.itemView.tag as Int
                val stringId = id.toString()
                var uri = TaskContract.TaskEntry.CONTENT_URI
                uri = uri.buildUpon().appendPath(stringId).build()

                contentResolver.delete(uri, null, null)

                supportLoaderManager.restartLoader(TASK_LOADER_ID, null, this@MainActivity)
            }
        }).attachToRecyclerView(mRecyclerView)

        val fabButton = findViewById<View>(R.id.fab) as FloatingActionButton
        fabButton.setOnClickListener {
            val addTaskIntent = Intent(baseContext, AddTaskActivity::class.java)
            startActivity(addTaskIntent)
        }

        supportLoaderManager.initLoader(TASK_LOADER_ID, null, this)

        tasksVM.taskList.value = listOf(
            Task(1,"124",1),
            Task(2,"2567",3))


        tasksVM.taskList.observe(  this, {
            Log.i("INFO","task list changed")
            mAdapter!!.swapCursor(tasksVM.taskList.value)
        })
    }

    override fun onResume() {
        super.onResume()
        supportLoaderManager.restartLoader(TASK_LOADER_ID, null, this)
    }

    override fun onCreateLoader(id: Int, loaderArgs: Bundle?): Loader<Cursor?> {
        return CursorLoader(this, TaskContract.TaskEntry.CONTENT_URI,
            null,null , null, TaskContract.TaskEntry.COLUMN_PRIORITY);
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        //mAdapter!!.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        //mAdapter!!.swapCursor(null)
    }

}