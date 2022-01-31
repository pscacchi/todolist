package ar.com.scacchipa.myclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ar.com.scacchipa.myclient.databinding.ActivityMainBinding
import ar.com.scacchipa.myclient.fragment.TaskListFragment
import ar.com.scacchipa.mylibrary.IFragManager
import ar.com.scacchipa.mylibrary.data.TaskViewModel

class MainActivity : AppCompatActivity(), IFragManager {

    private lateinit var tasksVM: TaskViewModel
    private lateinit var taskListFragment: TaskListFragment

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tasksVM = ViewModelProvider.AndroidViewModelFactory(application!!).create(TaskViewModel::class.java)
        this.initFragments()
   }

    /**
     * IFragFragment implementation
     */

    override fun initFragments() {
        taskListFragment = TaskListFragment(tasksVM, this)
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
        // TODO not implemented
    }
}