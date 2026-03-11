package com.gilorroristore.todoapp.addtasks.domain.repositories

import com.gilorroristore.todoapp.addtasks.ui.model.TaskModel

interface TaskRepository {
    suspend fun add(taskModel: TaskModel)
    suspend fun update(taskModel: TaskModel)
    suspend fun delete(taskModel: TaskModel)
}