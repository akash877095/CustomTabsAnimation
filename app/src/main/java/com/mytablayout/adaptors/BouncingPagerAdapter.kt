package com.mytablayout.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mytablayout.R
import com.mytablayout.databinding.BounceItemViewBinding

class BouncingPagerAdapter() : RecyclerView.Adapter<BouncingPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BounceItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = "MyCustomTag$position"
    }

    override fun getItemCount(): Int {
        return 6
    }

    class ViewHolder(val binding: BounceItemViewBinding) : RecyclerView.ViewHolder(binding.root) {}

}
