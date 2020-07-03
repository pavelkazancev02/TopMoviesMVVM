package com.pavelkazancev02.indeedidtest.data.room_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "saved_movies_table")
data class SavedMovie(
    @PrimaryKey(autoGenerate = true)
    val movieId: Long = 0L,
    @ColumnInfo(name = "title")
    val title: String = "title",
    @ColumnInfo(name = "release_date")
    val releaseDate: String = "release_date",
    @ColumnInfo(name = "popularity")
    val popularity: String = "popularity",
    @ColumnInfo(name = "poster_link")
    val posterLink: String = "poster_link"

)