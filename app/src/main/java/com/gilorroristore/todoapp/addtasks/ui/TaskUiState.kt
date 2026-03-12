package com.gilorroristore.todoapp.addtasks.ui

import com.gilorroristore.todoapp.addtasks.ui.model.TaskModel

sealed class TaskUiState {
    data object Loading: TaskUiState()
    data class Error(val throwable: Throwable): TaskUiState()
    data class Success(val tasks: List<TaskModel>) : TaskUiState()
}