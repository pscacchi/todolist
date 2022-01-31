package ar.com.scacchipa.myclient.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.scacchipa.myclient.databinding.TaskListBinding
import ar.com.scacchipa.mylibrary.IFragManager
import ar.com.scacchipa.mylibrary.data.CustomCursorAdapter
import ar.com.scacchipa.mylibrary.data.TaskViewModel

class TaskListFragment(
    val tasksVM: TaskViewModel,
    val callback: IFragManager): Fragment() {

    lateinit var binding: TaskListBinding
    private var mAdapter: CustomCursorAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskListBinding.inflate(inflater)

        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(context!!)
        mAdapter = CustomCursorAdapter(context!!)
        binding.recyclerViewTasks.adapter = mAdapter

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                tasksVM.delete(viewHolder.itemView.tag as Int)
            }
        }).attachToRecyclerView(binding.recyclerViewTasks)

        tasksVM.taskList.observe(this, {
            Log.i("INFO", "task list changed: ${it.toString()}")
            mAdapter!!.swapCursor(tasksVM.taskList.value)
        })
        return binding.root
    }
}