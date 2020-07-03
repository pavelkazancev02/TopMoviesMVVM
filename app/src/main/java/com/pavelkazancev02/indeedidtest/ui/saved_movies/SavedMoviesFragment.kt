package com.pavelkazancev02.indeedidtest.ui.saved_movies

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pavelkazancev02.indeedidtest.data.room_db.SavedMoviesDatabase
import com.pavelkazancev02.indeedidtest.databinding.FragmentSavedMoviesBinding
import com.pavelkazancev02.indeedidtest.ui.activities.LoginActivity
import com.pavelkazancev02.indeedidtest.ui.saved_movies.recycler_view.DeleteClickListener
import com.pavelkazancev02.indeedidtest.ui.saved_movies.recycler_view.SavedMoviesAdapter

class SavedMoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSavedMoviesBinding.inflate(inflater)

        val dataSource = SavedMoviesDatabase.getInstance(requireNotNull(this.activity).application).savedMoviesDatabaseDao

        val viewModelFactory = SavedMoviesViewModelFactory(dataSource)

        val savedMoviesViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(SavedMoviesViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.savedMoviesViewModel = savedMoviesViewModel

        val adapter = SavedMoviesAdapter(DeleteClickListener {
                movieTitle ->  savedMoviesViewModel.onDeleteClicked(movieTitle)
        })

        binding.savedMoviesRecyclerView.adapter = adapter

        savedMoviesViewModel.allMovies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.savedMoviesExit.setOnClickListener{
            startActivity(Intent(this.context, LoginActivity::class.java))
            activity?.finish()
        }

        return binding.root
    }


}