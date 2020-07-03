package com.pavelkazancev02.indeedidtest.data.room_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SavedMoviesDatabaseDao {
    @Insert
    fun insert(movie: SavedMovie)
    @Update
    fun update(movie: SavedMovie)
    @Query ("SELECT * from saved_movies_table WHERE movieId = :key")
    fun getByKey(key: Long): SavedMovie?
    @Query ("SELECT * from saved_movies_table WHERE title = :title")
    fun getByTitle(title: String): SavedMovie?
    @Query ("DELETE from saved_movies_table WHERE title = :title")
    fun removeByTitle(title: String)
    @Query("DELETE FROM saved_movies_table")
    fun clear()
    @Query("SELECT * FROM saved_movies_table ORDER BY movieId DESC")
    fun getAllMovies(): LiveData<List<SavedMovie>>
    @Query("SELECT * FROM saved_movies_table ORDER BY movieId DESC")
    fun getAllMoviesList(): List<SavedMovie>?
    @Query("SELECT * FROM saved_movies_table ORDER BY movieId DESC LIMIT 1")
    fun getLastMovie(): SavedMovie?
}