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

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android.todolist.CustomCursorAdapter.TaskViewHolder
import com.example.android.todolist.data.Task

class CustomCursorAdapter(private val mContext: Context) : RecyclerView.Adapter<TaskViewHolder>() {

    private var mTasks: List<Task>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.task_layout, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        // Indices for the _id, description, and priority columns
        /*
         val idIndex = mTasks!!.getColumnIndex(TaskContract.TaskEntry._ID)
         val descriptionIndex = mTasks!!.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION)
         val priorityIndex = mTasks!!.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY)
         mTasks!!.moveToPosition(position) // get to the right location in the cursor
        */
        //Set values
        holder.itemView.tag = mTasks!![position].id
        holder.taskDescriptionView.text = mTasks!![position].description
        holder.priorityView.text = mTasks!![position].priority.toString()

        val priorityCircle = holder.priorityView.background as GradientDrawable
        priorityCircle.setColor(getPriorityColor(mTasks!![position].priority))
    }

    /*
    Helper method for selecting the correct priority circle color.
    P1 = red, P2 = orange, P3 = yellow
    */
    private fun getPriorityColor(priority: Int): Int {
        var priorityColor = 0
        when (priority) {
            1 -> priorityColor = ContextCompat.getColor(mContext, R.color.materialRed)
            2 -> priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange)
            3 -> priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow)
            else -> {}
        }
        return priorityColor
    }

    override fun getItemCount(): Int {
        return if (mTasks == null) {
            0
        } else mTasks!!.size
    }

    fun swapCursor(c: List<Task>?): List<Task>? {
        if (mTasks === c) {
            return null
        }
        val temp = mTasks
        mTasks = c

        if (c != null) {
            notifyDataSetChanged()
        }
        return temp
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskDescriptionView: TextView = itemView.findViewById<View>(R.id.taskDescription) as TextView
        var priorityView: TextView = itemView.findViewById<View>(R.id.priorityTextView) as TextView
    }
}