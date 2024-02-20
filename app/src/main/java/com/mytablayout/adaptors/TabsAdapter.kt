package com.mytablayout.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mytablayout.DataModalClass
import com.mytablayout.R
import com.mytablayout.databinding.TabLayoutItemBinding

class TabsAdapter(private val dataList: ArrayList<DataModalClass>) :
    RecyclerView.Adapter<TabsAdapter.ViewHolder>() {

    private lateinit var callBack: View.OnClickListener
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val binding = TabLayoutItemBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return ViewHolder(binding)
    }

    fun setOnClickListener(callBack: View.OnClickListener) {
        this.callBack = callBack
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data, position)
    }

    inner class ViewHolder(private val binding: TabLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataModalClass, position: Int) {

            if (data.isSelected) {
                binding.tabIcon.setImageResource(data.selectedIcon)
                binding.tabRoot.background =
                    ContextCompat.getDrawable(mContext, R.drawable.selected_tab_back)
                binding.tabText.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                binding.tabText.visibility = View.VISIBLE
            } else {
                binding.tabIcon.setImageResource(data.unselectedIcon)
                binding.tabRoot.background = null
                binding.tabText.visibility = View.GONE
            }

            binding.tabText.text = data.title
            binding.tabRoot.setOnClickListener(callBack)
            binding.tabRoot.tag = position
        }

    }

}