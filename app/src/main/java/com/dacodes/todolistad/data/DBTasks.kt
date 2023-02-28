package com.dacodes.todolistad.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dacodes.todolistad.model.Task

@Database(
    entities = [Task::class],
    version = 1, exportSchema = true
)

abstract class DBTasks : RoomDatabase() {

    abstract fun taskDAO(): DAOTasks

    companion object {

        @Volatile
        private var INSTANCE: DBTasks? = null

        fun getDataSetClient(context: Context): DBTasks {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, DBTasks::class.java, "DBTasks")
                    .build()

                return INSTANCE!!
            }
        }
    }

}