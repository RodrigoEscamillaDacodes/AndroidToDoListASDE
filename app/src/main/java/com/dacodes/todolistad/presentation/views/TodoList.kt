package com.dacodes.todolistad.presentation.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dacodes.todolistad.R
import com.dacodes.todolistad.presentation.viewModels.TodoListViewModel
import com.dacodes.todolistad.databinding.TodoListFragmentBinding
import com.dacodes.todolistad.model.Task
import com.dacodes.todolistad.presentation.adapters.TasksAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoList : Fragment(), TasksAdapter.OnTaskClickListener, BottomSheetFragment.AddNewTaskListener {

    private lateinit var binding: TodoListFragmentBinding

    private lateinit var viewModel: TodoListViewModel
    private lateinit var adapter : TasksAdapter
    private lateinit var adapterUncompleted : TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TodoListFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[TodoListViewModel::class.java]
        viewModel.bind()

        viewModel.apply {
            completedList.observe(requireActivity(), Observer {
                adapter.setUpList(it)
            })

            unCompletedList.observe(requireActivity(), Observer {
                adapterUncompleted.setUpList(it)
            })
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TasksAdapter(requireContext(), this)
        adapterUncompleted = TasksAdapter(requireContext(), this)

        binding.apply {

            binding.inToday.tvSecondary.text = getString(R.string.today)
            binding.inCompleted.tvSecondary.text = getString(R.string.completed)

            rvUncompletedTasks.layoutManager = LinearLayoutManager(context)
            rvUncompletedTasks.adapter = adapterUncompleted
            rvUncompletedTasks.setHasFixedSize(true)

            rvTasks.layoutManager = LinearLayoutManager(context)
            rvTasks.adapter = adapter
            rvTasks.setHasFixedSize(true)

            fabAdd.setOnClickListener {
                val mBottomSheetFragment =
                    BottomSheetFragment.newInstance(this@TodoList)
                mBottomSheetFragment.isCancelable = true
                mBottomSheetFragment.show(requireActivity().supportFragmentManager, "ADD_TASK_BOTTOM_SHEET")
            }

        }

    }

    override fun deleteTask(item: Task) {
        viewModel.deleteTask(taskId = item.id)
    }

    override fun completedTask(item: Task) {
        viewModel.updateTask(item)
    }

    override fun onAddNewTask(task: Task) {
        viewModel.saveTask(task)
    }

}