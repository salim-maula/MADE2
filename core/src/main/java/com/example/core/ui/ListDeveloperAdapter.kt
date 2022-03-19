package com.example.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.databinding.ItemRowUserBinding
import com.example.core.domain.model.Developer

class ListDeveloperAdapter(
    private val developers: ArrayList<Developer>,
    private val clickListener: (Developer) -> Unit
) : RecyclerView.Adapter<ListDeveloperAdapter.ViewHolder>() {

    fun setListUser(items: List<Developer>?) {
        developers.apply {
            clear()
            items?.let { addAll(it) }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(developer: Developer, clickListener: (Developer) -> Unit) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(developer.avatarUrl)
                    .apply(RequestOptions())
                    .into(imgDev)
                tvDev.text = developer.username
                itemView.setOnClickListener {
                    clickListener(developer)
                }
            }
        }

    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListDeveloperAdapter.ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListDeveloperAdapter.ViewHolder, position: Int) {
        holder.bind(developers[position], clickListener)
    }

    override fun getItemCount(): Int = developers.size
}