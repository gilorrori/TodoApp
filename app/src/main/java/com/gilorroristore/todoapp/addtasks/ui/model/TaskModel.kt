package com.gilorroristore.todoapp.addtasks.ui.model

import com.gilorroristore.todoapp.addtasks.data.database.entities.TaskEntity

data class TaskModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val task: String,
    var selected: Boolean = false,
)

fun TaskModel.toDatabase() = TaskEntity(
    id = id,
    task = task,
    selected = selected
)