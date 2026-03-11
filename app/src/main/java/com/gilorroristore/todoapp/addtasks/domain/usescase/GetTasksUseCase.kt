package com.gilorroristore.todoapp.addtasks.domain.usescase

import com.gilorroristore.todoapp.addtasks.data.repositories.TaskRepositoryImpl
import com.gilorroristore.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val repositoryImpl: TaskRepositoryImpl) {
    operator fun invoke(): Flow<List<TaskModel>> = repositoryImpl.tasks
}