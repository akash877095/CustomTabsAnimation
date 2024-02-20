package com.mytablayout.controls.verticalviewpager

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mytablayout.R
import com.mytablayout.controls.verticalviewpager.modals.VerticalRankPriceDetails
import com.mytablayout.databinding.VerticalViewPagerBinding

class CustomRankVerticalPagerControl @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: VerticalViewPagerBinding
    private lateinit var pagerAdapter: PagerAdapter
    private var indicatorCount: Int = 0

    init { initView(context) }

    private fun initView(context: Context) {
        binding = VerticalViewPagerBinding.inflate(LayoutInflater.from(context), this, true)
        Log.e("hfhfhfhfhfhfh", "initView")
        setupViewPager()
        setupIndicator()
    }

    private fun setupViewPager() {

        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        pagerAdapter = PagerAdapter()
        binding.viewPager.adapter = pagerAdapter

        binding.viewPager.offscreenPageLimit = 1

        Log.e("hfhfhfhfhfhfh", "setupViewPager")

        val recyclerView = binding.viewPager.getChildAt(0) as RecyclerView
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.isItemPrefetchEnabled = true
        layoutManager.initialPrefetchItemCount = 1

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) { updateIndicators(position) }
        })

    }

    private fun setupIndicator() {

        binding.indicatorLayout.removeAllViews()
        indicatorCount = pagerAdapter.itemCount
        for (i in 0 until indicatorCount) {
            val indicatorView = createIndicatorView()
            binding.indicatorLayout.addView(indicatorView)
        }
        Log.e("hfhfhfhfhfhfh", "setupIndicator")

        updateIndicators(0)
    }

    private fun createIndicatorView(): View {
        val indicatorView = View(context)
        val layoutParams = LinearLayout.LayoutParams(context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._2sdp),
            context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp))
        layoutParams.setMargins(0, context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._2sdp), 0, 0)
        indicatorView.layoutParams = layoutParams
        indicatorView.background = ContextCompat.getDrawable(context, R.drawable.inactive_indicator_back)
        return indicatorView
    }

    private fun updateIndicators(currentPosition: Int) {
        Log.e("hfhfhfhfhfhfh", "updateIndicators")
        for (i in 0 until binding.indicatorLayout.childCount) {
            val indicatorView = binding.indicatorLayout.getChildAt(i)
            if (i == currentPosition) {
                indicatorView.background = ContextCompat.getDrawable(context, R.drawable.active_indicator_back)
            } else {
                indicatorView.background = ContextCompat.getDrawable(context, R.drawable.inactive_indicator_back)
            }
        }
    }

    fun setDataList(dataList: List<VerticalRankPriceDetails>) {
        Log.e("hfhfhfhfhfhfh", "setItemCount")
        pagerAdapter.setData(dataList)
        setupIndicator()
    }

}