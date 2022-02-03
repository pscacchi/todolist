package ar.com.scacchipa.todolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.scacchipa.todolist.data.IFragManager
import ar.com.scacchipa.todolist.data.TaskViewModel
import com.example.android.todolist.databinding.TaskListBinding

class TaskListFragment: Fragment() {

    val tasksVM: TaskViewModel by activityViewModels()
    var delegate: IFragManager? = null
    lateinit var binding: TaskListBinding
    private var mAdapter: CustomCursorAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IFragManager) {
            delegate = context
        }
        // delegate = context as? IFragment
    }

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

        binding.floatingActionButton.setOnClickListener {
            delegate?.showAddingFragment()
        }

        tasksVM.taskListModel.observe(this, {
            Log.i("INFO", "task list changed")
            mAdapter!!.swapCursor(tasksVM.taskListModel.value)
        })
        return binding.root
    }
}