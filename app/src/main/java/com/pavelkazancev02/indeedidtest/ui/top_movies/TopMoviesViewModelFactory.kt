package com.pavelkazancev02.indeedidtest.ui.top_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pavelkazancev02.indeedidtest.data.room_db.SavedMoviesDatabaseDao

class TopMoviesViewModelFactory(private val dataSource: SavedMoviesDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopMoviesViewModel::class.java)) {
            return TopMoviesViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}