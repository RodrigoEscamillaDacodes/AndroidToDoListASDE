package com.dacodes.todolistad.core

import android.content.Context
import com.dacodes.todolistad.data.DBTasks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun initializeDB(@ApplicationContext appContext: Context) : DBTasks  = DBTasks.getDataSetClient(appContext)

}