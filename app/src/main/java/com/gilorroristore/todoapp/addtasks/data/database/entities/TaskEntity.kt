package com.gilorroristore.todoapp.addtasks.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gilorroristore.todoapp.addtasks.ui.model.TaskModel

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey
    val id: Int,
    val task: String,
    var selected: Boolean = false,
)

fun TaskEntity.toDomain() = TaskModel(
    id = id,
    task = task,
    selected = selected
)