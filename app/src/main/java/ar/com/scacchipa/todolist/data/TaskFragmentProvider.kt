package ar.com.scacchipa.todolist.data

import android.os.Bundle
import androidx.fragment.app.Fragment

class TaskFragmentProvider(private val viewModel: TaskViewModel) {
}

class MyFrag: Fragment() {
    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
    }
}
