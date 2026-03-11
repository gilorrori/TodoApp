package com.gilorroristore.todoapp.addtasks.domain.usescase

import com.gilorroristore.todoapp.addtasks.data.repositories.TaskRepositoryImpl
import com.gilorroristore.todoapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(private val repositoryImpl: TaskRepositoryImpl) {
    suspend operator fun invoke(taskModel: TaskModel) {
        repositoryImpl.update(taskModel)
    }
}