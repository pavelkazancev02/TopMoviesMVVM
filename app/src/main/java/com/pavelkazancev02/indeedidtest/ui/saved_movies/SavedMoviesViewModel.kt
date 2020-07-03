package com.pavelkazancev02.indeedidtest.ui.saved_movies

import android.util.Log
import androidx.lifecycle.ViewModel
import com.pavelkazancev02.indeedidtest.data.room_db.SavedMoviesDatabaseDao
import kotlinx.coroutines.*
class SavedMoviesViewModel(val database: SavedMoviesDatabaseDao) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val allMovies = database.getAllMovies()


    init{

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onDeleteClicked(movieTitle: String){
        Log.i("test15", "clicked")
        uiScope.launch {
            removeMovieFromDatabase(movieTitle)
        }
    }

    private suspend fun removeMovieFromDatabase(movieTitle: String) {
        uiScope.launch {
            remove(movieTitle)
        }
    }

    private suspend fun remove(movieTitle: String) {
        withContext(Dispatchers.IO) {
            database.removeByTitle(movieTitle)
        }
    }

}