package com.task.paymob.datasource.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.task.paymob.model.Movie


@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val movieDao : MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDao? = null

        fun getDaoInstance(context: Context): MovieDao {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).movieDao
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).fallbackToDestructiveMigration().build()
        }
    }
}
