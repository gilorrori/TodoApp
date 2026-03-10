package com.gilorroristore.todoapp.addtasks.data.di

import android.content.Context
import androidx.room.Room
import com.gilorroristore.todoapp.addtasks.data.database.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val TASK_DATABASE_NAME = "task_database"

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(context, TodoDatabase::class.java, TASK_DATABASE_NAME).build()

   @Provides
   @Singleton
   fun provideDao(db : TodoDatabase) = db.taskDao()
}