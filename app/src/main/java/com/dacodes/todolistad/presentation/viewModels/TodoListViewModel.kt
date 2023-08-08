package com.dacodes.todolistad.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacodes.todolistad.data.repository.TaskRepository
import com.dacodes.todolistad.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {

    private var _completedList = MutableLiveData<ArrayList<Task>>()
    val completedList : LiveData<ArrayList<Task>> get() = _completedList

    private var _unCompletedList = MutableLiveData<ArrayList<Task>>()
    val unCompletedList : LiveData<ArrayList<Task>> get() = _unCompletedList

    fun bind(){
        deleteOutDateTasks()
    }

    private fun deleteOutDateTasks(){

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = LocalDateTime.now().format(formatter)

        runBlocking {
            val result = taskRepository.getTasks()

            result.forEach {
                if (it.date < currentDate) {
                    taskRepository.deleteTask(it.id)
                }
            }

            getTasks()
        }


    }

    private fun getTasks(){
        viewModelScope.launch {
            _unCompletedList.value = ArrayList(taskRepository.getTasks(false))
            _completedList.value = ArrayList(taskRepository.getTasks(true))
        }
    }

    fun saveTask(task: Task){
        runBlocking {
            taskRepository.saveTask(model = task)
            getTasks()
        }
    }

    fun updateTask(task: Task){
        runBlocking {
            taskRepository.updateTask(model = task)
            getTasks()
        }
    }

    fun deleteTask(taskId: Int){
        runBlocking {
            taskRepository.deleteTask(taskId = taskId)
            getTasks()
        }
    }

}