package com.gilorroristore.todoapp.addtasks.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gilorroristore.todoapp.addtasks.data.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table")
    fun getTasks() : Flow<List<TaskEntity>>

    @Insert
    suspend fun addTask(taskEntity: TaskEntity)
}