package com.salman.tmdb.ui.listingfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.salman.domain.response.Result
import com.salman.tmdb.R
import com.salman.tmdb.databinding.ItemMovieBinding
import com.salman.tmdb.extensions.setImageUrl
import com.salman.tmdb.utils.Utils
import com.salman.tmdb.utils.Utils.W300

/**
@author Salman Aziz
created on 7/3/22
 **/

class MoviesAdapter : ListAdapter<Result, MoviesAdapter.PostsViewHolder>(DiffCallBack) {

    lateinit var movieClickListener: MovieClickListener<Result>

    class PostsViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)
    object DiffCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {

        val item = getItem(position)

        holder.binding.tvTitle.text = item.title


        holder.binding.ivPostImage.setImageUrl(
            Utils.getImagePath(W300,item.posterPath ), placeholder = AppCompatResources
                .getDrawable(holder.binding.ivPostImage.context, R.drawable.image_place_holder)
        )

        holder.itemView.setOnClickListener {
            movieClickListener.invoke(item)
        }
    }

}
typealias MovieClickListener<T> = (data: T) -> Unit
