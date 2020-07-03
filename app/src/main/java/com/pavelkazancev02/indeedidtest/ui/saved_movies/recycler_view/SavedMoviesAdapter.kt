package com.pavelkazancev02.indeedidtest.ui.saved_movies.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pavelkazancev02.indeedidtest.data.POSTER_BASE_URL
import com.pavelkazancev02.indeedidtest.data.room_db.SavedMovie
import com.pavelkazancev02.indeedidtest.databinding.ListItemSavedMoviesBinding
import kotlinx.android.synthetic.main.list_item_saved_movies.view.*

class SavedMoviesAdapter(val deleteClickListener: DeleteClickListener) :
    androidx.recyclerview.widget.ListAdapter<SavedMovie, SavedMoviesAdapter.ViewHolder>(
        SavedMoviesDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, deleteClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: ListItemSavedMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: SavedMovie, deleteClickListener: DeleteClickListener
        ) {
            binding.movie = item
            var moviePosterURL = "";
            if (! item?.posterLink.isNullOrEmpty()) moviePosterURL = POSTER_BASE_URL +item?.posterLink;
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.saved_movie_poster);
            binding.movieTitle.text = item.title
            binding.releaseDate.text = item.releaseDate
            binding.popularityScore.text = item.popularity.toString()
            binding.deleteClickListener = deleteClickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                   ListItemSavedMoviesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class SavedMoviesDiffCallback : DiffUtil.ItemCallback<SavedMovie>() {
    override fun areItemsTheSame(
        oldItem: SavedMovie,
        newItem: SavedMovie
    ): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(
        oldItem: SavedMovie,
        newItem: SavedMovie
    ): Boolean {
        return oldItem == newItem
    }

}
class DeleteClickListener(val clickListener: (moviesTitle: String) -> Unit) {
    fun onDeleteClick(movie: SavedMovie) = clickListener(movie.title)
}
