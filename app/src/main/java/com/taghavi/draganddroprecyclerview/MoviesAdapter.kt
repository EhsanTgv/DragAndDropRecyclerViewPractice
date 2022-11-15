package com.taghavi.draganddroprecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taghavi.draganddroprecyclerview.databinding.MovieCardBinding

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(
            MovieCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movieItem = differ.currentList[position]
        holder.bindView(movieItem)
    }

    private val differCallBack = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.contentEquals(newItem)
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.contentEquals(newItem)
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    fun moveItem(from: Int, to: Int) {
        val list = differ.currentList.toMutableList()
        val fromLocation = list[from]
        list.removeAt(from)
        if (to < from) {
            list.add(to + 1, fromLocation)
        } else {
            list.add(to - 1, fromLocation)
        }
        differ.submitList(list)
    }

    inner class MoviesViewHolder(private val itemViewBinding: MovieCardBinding) :
        RecyclerView.ViewHolder(
            itemViewBinding.root
        ) {
        fun bindView(movieItem: String) {
            itemViewBinding.apply {
                tvName.text = movieItem
            }

            itemViewBinding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(movieItem)
                }
            }
        }
    }
}