package com.dacodes.todolistad.data

import androidx.room.*
import com.dacodes.todolistad.model.Task

@Dao
interface DAOTasks {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM Task")
    suspend fun getTasks():List<Task>
    @Query("SELECT * FROM Task WHERE completed = :completed")
    suspend fun getTasksByStatus(completed: Boolean):List<Task>

    @Update(entity = Task::class)
    suspend fun updateTask(model: Task)

    @Query("DELETE FROM Task WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

}