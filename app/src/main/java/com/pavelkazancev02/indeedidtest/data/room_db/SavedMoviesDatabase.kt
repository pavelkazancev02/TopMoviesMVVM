package com.pavelkazancev02.indeedidtest.data.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedMovie::class], version = 1,  exportSchema = false)
abstract class SavedMoviesDatabase : RoomDatabase() {
    abstract val savedMoviesDatabaseDao: SavedMoviesDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: SavedMoviesDatabase? = null
        fun getInstance(context: Context): SavedMoviesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SavedMoviesDatabase::class.java,
                        "saved_movies_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}