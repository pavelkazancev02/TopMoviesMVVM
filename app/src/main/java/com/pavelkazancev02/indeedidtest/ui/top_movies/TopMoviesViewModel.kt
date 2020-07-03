package com.pavelkazancev02.indeedidtest.ui.top_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pavelkazancev02.indeedidtest.data.MoviesApi
import com.pavelkazancev02.indeedidtest.data.room_db.SavedMovie
import com.pavelkazancev02.indeedidtest.data.value_objects.MovieResponse
import com.pavelkazancev02.indeedidtest.data.room_db.SavedMoviesDatabaseDao
import com.pavelkazancev02.indeedidtest.data.value_objects.Movie
import kotlinx.coroutines.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopMoviesViewModel(val database: SavedMoviesDatabaseDao) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _topMovies = MutableLiveData<MovieResponse>()
    val topMovies : LiveData<MovieResponse>
        get() = _topMovies

    private val _showLoadingBar = MutableLiveData<Boolean>()
    val showLoadingBar: LiveData<Boolean>
        get() = _showLoadingBar


    init{
        _showLoadingBar.value=true
        getMoviesResponse()
    }

    private fun getMoviesResponse() {
        MoviesApi.getRetrofitService().getTopMovies(1).enqueue(object: Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                //TODO
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _topMovies.value = response.body()
                _showLoadingBar.value=false
            }
        })
    }

    fun onSaveClicked(movie: Movie){
        uiScope.launch {
            addMovieToDatabase(movie)
        }
    }

    private suspend fun addMovieToDatabase(movie: Movie) {
        uiScope.launch {
            val newMovie = SavedMovie(title = movie.title, releaseDate = movie.releaseDate, popularity = movie.popularity.toString(), posterLink = movie.posterPath)
            insert(newMovie)
        }
    }

    private suspend fun insert(movie: SavedMovie) {
        withContext(Dispatchers.IO) {
            if (database.getByTitle(movie.title)==null) database.insert(movie)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}