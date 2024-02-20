package com.mytablayout.adaptors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mytablayout.R
import com.mytablayout.databinding.ProductAdapterBinding
import com.mytablayout.modals.ProductsData

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var mItems: ArrayList<ProductsData> = ArrayList()

    private lateinit var callback: View.OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ProductAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setOnClickListener(callback: View.OnClickListener) {
        this.callback = callback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(mItems: ArrayList<ProductsData>) {
        this.mItems = mItems
        notifyDataSetChanged()
    }

    fun getItem(): ArrayList<ProductsData> {
        return mItems
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mItems[position]
        holder.bindData(data,position)
    }

    inner class ViewHolder(private val binding: ProductAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: ProductsData, position: Int) {
            binding.clRootView.tag = position
            binding.clRootView.setOnClickListener(callback)

            if(data.isSelected) {
                binding.clRootView.setBackgroundResource(R.drawable.white_round_back)
            } else {
                binding.clRootView.setBackgroundResource(R.drawable.product_trans_gradient_back)
            }

        }
    }

}