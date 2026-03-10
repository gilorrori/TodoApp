package com.gilorroristore.todoapp.addtasks.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gilorroristore.todoapp.addtasks.data.database.dao.TaskDao
import com.gilorroristore.todoapp.addtasks.data.database.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    //DAO
    abstract fun taskDao(): TaskDao
}