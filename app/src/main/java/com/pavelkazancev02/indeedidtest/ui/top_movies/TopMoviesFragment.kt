package com.pavelkazancev02.indeedidtest.ui.top_movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pavelkazancev02.indeedidtest.databinding.FragmentTopMoviesBinding
import com.pavelkazancev02.indeedidtest.ui.top_movies.recycler_view.SaveClickListener
import com.pavelkazancev02.indeedidtest.ui.top_movies.recycler_view.TopMoviesAdapter
import com.pavelkazancev02.indeedidtest.data.room_db.SavedMoviesDatabase
import com.pavelkazancev02.indeedidtest.ui.activities.LoginActivity

class TopMoviesFragment : Fragment() {

    private val viewModel: TopMoviesViewModel by lazy {
        ViewModelProviders.of(this).get(TopMoviesViewModel::class.java)
    }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val binding = FragmentTopMoviesBinding.inflate(inflater)


            val dataSource = SavedMoviesDatabase.getInstance(requireNotNull(this.activity).application).savedMoviesDatabaseDao

            val viewModelFactory = TopMoviesViewModelFactory(dataSource)

            val topMoviesViewModel =
                ViewModelProviders.of(
                    this, viewModelFactory
                ).get(TopMoviesViewModel::class.java)

            binding.setLifecycleOwner(this)

            binding.viewModel = topMoviesViewModel


            val adapter = TopMoviesAdapter(SaveClickListener {
                    movie ->  topMoviesViewModel.onSaveClicked(movie)
            })


            binding.topMoviesRecyclerView.adapter = adapter

            //Updating RecyclerView live-time
            viewModel.topMovies.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it.results)
            })

            topMoviesViewModel.showLoadingBar.observe(viewLifecycleOwner, Observer { showEvent ->
                showEvent?.let {
                    if (it) {
                        binding.loadingBar.visibility = View.VISIBLE
                    } else {
                        binding.loadingBar.visibility = View.GONE
                    }
                }
            })

            binding.topMoviesExit.setOnClickListener{
                startActivity(Intent(this.context, LoginActivity::class.java))
                activity?.finish()
            }

            return binding.root
        }


    }