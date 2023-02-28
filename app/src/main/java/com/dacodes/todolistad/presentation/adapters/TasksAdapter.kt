package com.dacodes.todolistad.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dacodes.todolistad.R
import com.dacodes.todolistad.databinding.TaskItemBinding
import com.dacodes.todolistad.model.Task

class TasksAdapter(
    private val context: Context,
    private val onClick: OnTaskClickListener,
) : RecyclerView.Adapter<BaseViewHolder<*>>(){

    private var list: List<Any> = emptyList()

    interface OnTaskClickListener{
        fun deleteTask(item: Task)
        fun completedTask(item: Task)
    }

    fun setUpList(list: List<Task>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return TaskViewHolder(
            LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)
        )
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

                cbCompleted.isChecked = item.completed

                var color = when(item.priority){
                    1-> ContextCompat.getDrawable(context, R.drawable.high)
                    2-> ContextCompat.getDrawable(context, R.drawable.normal)
                    3-> ContextCompat.getDrawable(context, R.drawable.low)
                    else ->  ContextCompat.getDrawable(context, R.drawable.high)
                }

                ivPriority.setImageDrawable(color)

                cbCompleted.setOnCheckedChangeListener { _, checked ->
                    item.completed = checked
                    onClick.completedTask(item)
                }

                ibDelete.setOnClickListener {
                    onClick.deleteTask(item)
                }

            }
        }
    }

}