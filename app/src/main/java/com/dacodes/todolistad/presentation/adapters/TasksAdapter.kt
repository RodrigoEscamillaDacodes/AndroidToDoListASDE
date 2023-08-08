package com.dacodes.todolistad.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dacodes.todolistad.R
import com.dacodes.todolistad.databinding.TaskItemBinding
import com.dacodes.todolistad.model.Task

class TasksAdapter(
    private val context: Context,
    private val onClick: OnTaskClickListener,
) : RecyclerView.Adapter<BaseViewHolder<*>>(){

    private var list: List<Task> = arrayListOf()

    interface OnTaskClickListener{
        fun deleteTask(item: Task)
        fun completedTask(item: Task)
    }

    fun setUpList(list: List<Task>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.task_item, parent, false))

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = list[position]
        when(holder){
            is TaskViewHolder -> holder.bind(element as Task, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class TaskViewHolder(itemView: View) : BaseViewHolder<Task>(itemView){
        private val binding = TaskItemBinding.bind(itemView)
        override fun bind(item: Task, position: Int) {
            binding.apply {

                tvTask.text = item.task

                tvNumber.text = "${item.priority}"

                if (item.completed){
                    llPriority.visibility = GONE
                }

                tvTask.setOnClickListener {
                    item.completed = !item.completed
                    onClick.completedTask(item)
                }

                cvParent.setOnClickListener{
                    item.completed = !item.completed
                    onClick.completedTask(item)
                }

                ibCompleted.setOnClickListener {
                    item.completed = !item.completed
                    onClick.completedTask(item)
                }

                ibDelete.setOnClickListener {
                    onClick.deleteTask(item)
                }

            }
        }
    }

}