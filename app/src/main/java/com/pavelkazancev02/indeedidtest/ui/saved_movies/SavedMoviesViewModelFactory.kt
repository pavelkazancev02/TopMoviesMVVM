package com.pavelkazancev02.indeedidtest.ui.saved_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pavelkazancev02.indeedidtest.data.room_db.SavedMoviesDatabaseDao

class SavedMoviesViewModelFactory (private val dataSource: SavedMoviesDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedMoviesViewModel::class.java)) {
            return SavedMoviesViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}