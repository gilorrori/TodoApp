package com.gilorroristore.todoapp.addtasks.data.repositories

import com.gilorroristore.todoapp.addtasks.data.database.dao.TaskDao
import com.gilorroristore.todoapp.addtasks.data.database.entities.toDomain
import com.gilorroristore.todoapp.addtasks.domain.repositories.TaskRepository
import com.gilorroristore.todoapp.addtasks.ui.model.TaskModel
import com.gilorroristore.todoapp.addtasks.ui.model.toDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TaskRepository {

    val tasks: Flow<List<TaskModel>> = taskDao.getTasks().map { items ->
        items.map {
            it.toDomain()
        }
    }

    override suspend fun add(taskModel: TaskModel) {
        taskDao.addTask(taskEntity = taskModel.toDatabase())
    }

    override suspend fun update(taskModel: TaskModel) {
        taskDao.updateTask(taskModel.toDatabase())
    }

    override suspend fun delete(taskModel: TaskModel) {
        taskDao.deleteTask(taskModel.toDatabase())
    }
}