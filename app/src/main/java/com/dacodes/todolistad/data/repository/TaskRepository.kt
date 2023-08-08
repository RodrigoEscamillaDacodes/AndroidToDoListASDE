package com.dacodes.todolistad.data.repository

import com.dacodes.todolistad.data.DBTasks
import com.dacodes.todolistad.model.Task
import javax.inject.Inject


class TaskRepository @Inject constructor(private val dbTasks: DBTasks) {

    suspend fun saveTask( model: Task){
        dbTasks.taskDAO().insertTask(model)
    }

    suspend fun getTasks() : List<Task>{
        return dbTasks.taskDAO().getTasks()
    }

    suspend fun getTasks(completed: Boolean) : List<Task>{
        return dbTasks.taskDAO().getTasksByStatus(completed)
    }

    suspend fun deleteTask(taskId: Int) {
        dbTasks.taskDAO().deleteTask(taskId)
    }

    suspend fun updateTask(model: Task){
        dbTasks.taskDAO().updateTask(model)
    }

}