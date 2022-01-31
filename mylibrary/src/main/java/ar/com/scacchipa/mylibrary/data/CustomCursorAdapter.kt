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
package ar.com.scacchipa.mylibrary.data

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ar.com.scacchipa.mylibrary.R

class CustomCursorAdapter(private val mContext: Context) : RecyclerView.Adapter<CustomCursorAdapter.TaskViewHolder>() {

    private var mTasks: List<Task>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.task_layout, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

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

    fun swapCursor(tasks: List<Task>?) {
        mTasks = tasks
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskDescriptionView: TextView = itemView.findViewById<View>(R.id.taskDescription) as TextView
        var priorityView: TextView = itemView.findViewById<View>(R.id.priorityTextView) as TextView
    }
}