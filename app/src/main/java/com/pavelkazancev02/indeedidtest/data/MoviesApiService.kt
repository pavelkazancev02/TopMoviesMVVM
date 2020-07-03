package com.pavelkazancev02.indeedidtest.data

import com.pavelkazancev02.indeedidtest.data.value_objects.MovieResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

var retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface MoviesApiService {

    @GET("movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1")
    fun getTopMovies(@Query("page") page: Int): Call<MovieResponse>

}

object MoviesApi {

    fun getRetrofitService(): MoviesApiService{
        return retrofit.create(MoviesApiService::class.java)
    }

}