package com.mytablayout.controls.verticalviewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mytablayout.controls.verticalviewpager.modals.VerticalRankPriceDetails
import com.mytablayout.databinding.PagerAdapterBinding

class PagerAdapter : RecyclerView.Adapter<PagerAdapter.ViewHolder>() {

    private val data: ArrayList<VerticalRankPriceDetails> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PagerAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = data[position]
        holder.bind(itemData)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(dataList: List<VerticalRankPriceDetails>) {
        data.clear()
        data.addAll(dataList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: PagerAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding = binding
        fun bind(data: VerticalRankPriceDetails) {
            binding.tvPrice.text = data.price
            binding.tvPriceText.text = data.priceText
            binding.image.setImageResource(data.image)
        }

    }

}