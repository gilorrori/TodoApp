package com.gilorroristore.todoapp.addtasks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilorroristore.todoapp.addtasks.domain.usescase.AddTaskUseCase
import com.gilorroristore.todoapp.addtasks.domain.usescase.DeleteTaskUseCase
import com.gilorroristore.todoapp.addtasks.domain.usescase.GetTasksUseCase
import com.gilorroristore.todoapp.addtasks.domain.usescase.UpdateTaskUseCase
import com.gilorroristore.todoapp.addtasks.ui.TaskUiState.Success
import com.gilorroristore.todoapp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    getTasksUseCase: GetTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : ViewModel() {

    val uiState: StateFlow<TaskUiState> = getTasksUseCase().map(::Success)
        .catch { TaskUiState.Error(it) }
        //stateIn convierte un flow en un stateFlow // Sharing.. cuadno este en segundo plano se cancela el flow despues del tiempo y su estado inicial "loading"
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskUiState.Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    //private val _tasks = mutableStateListOf<TaskModel>()
    //val task: List<TaskModel> = _tasks


    fun onTaskCreated(task: String) {
        _showDialog.value = false
        viewModelScope.launch(Dispatchers.IO) {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        /*val index = _tasks.indexOf(taskModel)
        //Buscando un elemento detro de una mutableStateListOf
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }*/
        viewModelScope.launch(Dispatchers.IO) {
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }
    }

    fun onItemRemove(taskModel: TaskModel) {
        /* // Buscando un elemento que coincida en este caso con el id de nuestro listado
         val task = _tasks.find { it.id == taskModel.id }
         _tasks.remove(task)*/
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase(taskModel)
        }

    }

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onShowSelected() {
        _showDialog.value = true
    }
}