package com.pavelkazancev02.indeedidtest.ui.top_movies.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pavelkazancev02.indeedidtest.data.POSTER_BASE_URL
import com.pavelkazancev02.indeedidtest.data.value_objects.Movie
import com.pavelkazancev02.indeedidtest.databinding.ListItemTopMoviesBinding
import kotlinx.android.synthetic.main.list_item_top_movies.view.*

class TopMoviesAdapter(val saveClickListener: SaveClickListener) : androidx.recyclerview.widget.ListAdapter<Movie, TopMoviesAdapter.ViewHolder>(
    OperationsDiffCallback()
) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, saveClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: ListItemTopMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Movie, saveClickListener: SaveClickListener
        ) {
            binding.movie = item

            var moviePosterURL = "";
            if (! item?.posterPath.isNullOrEmpty()) moviePosterURL = POSTER_BASE_URL+item?.posterPath;
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.movie_poster);
            binding.movieTitle.text = item.title
            binding.releaseDate.text = item.releaseDate
            binding.popularityScore.text = item.popularity.toString()
            binding.saveClickListener = saveClickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ListItemTopMoviesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class OperationsDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean {
        return oldItem == newItem
    }

}

class SaveClickListener(val clickListener: (movie: Movie) -> Unit) {
    fun onSaveClick(movie: Movie) = clickListener(movie)
}