package com.dacodes.todolistad.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacodes.todolistad.data.repository.TaskRepository
import com.dacodes.todolistad.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {

    private var _tasks = MutableLiveData<List<Task>>()
    val tasks : LiveData<List<Task>> get() = _tasks

    fun bind(){
        getTasks()
    }

    private fun getTasks(){
        viewModelScope.launch {
            val result = withContext(Dispatchers.Main){
                taskRepository.getTasks()
            }
            _tasks.value = result
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
        }
    }

    fun deleteTask(taskId: Int){
        runBlocking {
            taskRepository.deleteTask(taskId = taskId)
            getTasks()
        }
    }

}