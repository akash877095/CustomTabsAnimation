package com.mytablayout.screens

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.mytablayout.DataModalClass
import com.mytablayout.R
import com.mytablayout.databinding.ActivityRecyclerViewBinding
import com.mytablayout.databinding.TabLayoutItemBinding

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewBinding
    private lateinit var dataList: ArrayList<DataModalClass>
    private var selectedItemPosition = 0
    private var isAlreadySelectedPosition = 0
    private var isAnimationInProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun addDynamicView(data: DataModalClass, i: Int) {

        val bindingTab = TabLayoutItemBinding.inflate(LayoutInflater.from(this), null, false)

        bindingTab.tabText.text = data.title

        if (i == selectedItemPosition) {
            // For the selected item
            bindingTab.tabIcon.setImageResource(data.selectedIcon)
            bindingTab.tabText.setTextColor(ContextCompat.getColor(this, R.color.white))
            bindingTab.tabRoot.background =
                ContextCompat.getDrawable(this, R.drawable.selected_tab_back)
            bindingTab.tabText.visibility = View.VISIBLE
        } else {
            // For the unselected item
            bindingTab.tabIcon.setImageResource(data.unselectedIcon)
            bindingTab.tabText.setTextColor(ContextCompat.getColor(this, R.color.white))
            bindingTab.tabRoot.background = null
            bindingTab.tabText.visibility = View.GONE
            bindingTab.tabText.layoutParams.width = 0
        }

        binding.llAddDynamicLayout.addView(bindingTab.root)

        bindingTab.tabRoot.setOnClickListener {

            scrollToNextItemIfNotVisible(i)
            scrollToPreviousItemIfNotVisible(i)
            // makeViewFullyVisible(it)

            Log.e("gfgfgfgfgfg", "is Last Item = ${i == binding.llAddDynamicLayout.childCount - 1}")

            if (!isAnimationInProgress && isAlreadySelectedPosition != i) {
                isAnimationInProgress = true

                val previousSelectedItem =
                    binding.llAddDynamicLayout.getChildAt(selectedItemPosition)
                val previousTabIcon = previousSelectedItem.findViewById<ImageView>(R.id.tabIcon)
                val previousTabText = previousSelectedItem.findViewById<TextView>(R.id.tabText)
                val previousTabRoot = previousSelectedItem.findViewById<LinearLayout>(R.id.tabRoot)

                collapseTabText(
                    previousTabText,
                    previousTabIcon,
                    previousTabText,
                    previousTabRoot,
                    dataList[selectedItemPosition].unselectedIcon
                )

                selectedItemPosition = i
                isAlreadySelectedPosition = selectedItemPosition

                bindingTab.tabIcon.setImageResource(data.selectedIcon)
                bindingTab.tabText.setTextColor(ContextCompat.getColor(this, R.color.white))
                bindingTab.tabRoot.background =
                    ContextCompat.getDrawable(this, R.drawable.selected_tab_back)
                bindingTab.tabText.visibility = View.VISIBLE

                expandTabText(bindingTab.tabText, i)
            }

        }

    }

    private fun scrollToNextItemIfNotVisible(currentClickedIndex: Int) {
        val nextItemIndex = currentClickedIndex + 1

        if (nextItemIndex < binding.llAddDynamicLayout.childCount) {

            val nextItem = binding.llAddDynamicLayout.getChildAt(nextItemIndex)

            val nextItemRight = nextItem.right
            val visibleRight =
                binding.horizontalScrollView.scrollX + binding.horizontalScrollView.width

            if (nextItemRight > visibleRight) {
                val scrollAmount = nextItemRight - visibleRight

                binding.horizontalScrollView.smoothScrollBy(scrollAmount, 0)
            }

        }

    }

    private fun scrollToPreviousItemIfNotVisible(currentClickedIndex: Int) {
        val previousItemIndex = currentClickedIndex - 1

        if (previousItemIndex >= 0) {
            val previousItem = binding.llAddDynamicLayout.getChildAt(previousItemIndex)

            val previousItemLeft = previousItem.left
            val visibleLeft = binding.horizontalScrollView.scrollX

            if (previousItemLeft < visibleLeft) {
                val scrollAmount = previousItemLeft - visibleLeft

                binding.horizontalScrollView.smoothScrollBy(scrollAmount, 0)
            }
        }
    }

    private fun initViews() {

        dataList = ArrayList()
        dataList.add(
            DataModalClass(
                R.drawable.game_selected,
                R.drawable.game_unselected,
                "Games Club",
                isSelected = true
            )
        )
        dataList.add(
            DataModalClass(
                R.drawable.popcorn_selected,
                R.drawable.popcorn_unselected,
                "myTV & Movies",
                isSelected = false
            )
        )
        dataList.add(
            DataModalClass(
                R.drawable.percent_selected,
                R.drawable.percent_unselected,
                "Vouchers & Promos",
                isSelected = false
            )
        )
        dataList.add(
            DataModalClass(
                R.drawable.mike_selected,
                R.drawable.mike_unselected,
                "Shorts",
                isSelected = false
            )
        )
        dataList.add(
            DataModalClass(
                R.drawable.audio_selected,
                R.drawable.audio_unselected,
                "Podcast",
                isSelected = false
            )
        )

        for (i in 0 until dataList.size) addDynamicView(dataList[i], i)

    }

    private fun collapseTabText(
        tabText: TextView,
        previousTabIcon: ImageView,
        previousTabText: TextView,
        previousTabRoot: LinearLayout,
        unselectedIcon: Int
    ) {
        tabText.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val initialWidth = tabText.measuredWidth
        val targetWidth = 0

        val animator = ValueAnimator.ofInt(initialWidth, targetWidth)
        animator.duration = 200

        animator.addUpdateListener { animation: ValueAnimator ->
            val value = animation.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = tabText.layoutParams
            layoutParams.width = value
            tabText.layoutParams = layoutParams
            tabText.requestLayout()
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                previousTabIcon.setImageResource(unselectedIcon)
                previousTabText.setTextColor(
                    ContextCompat.getColor(
                        this@RecyclerViewActivity,
                        R.color.white
                    )
                )
                previousTabRoot.background = null
                isAnimationInProgress = false
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })

        animator.start()
    }

    private fun expandTabText(tabText: TextView, position: Int) {

        tabText.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val initialWidth = 0
        val targetWidth = tabText.measuredWidth

        val animator = ValueAnimator.ofInt(initialWidth, targetWidth)
        animator.duration = 200

        animator.addUpdateListener { animation: ValueAnimator ->
            val value = animation.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = tabText.layoutParams
            layoutParams.width = value
            tabText.layoutParams = layoutParams
            tabText.requestLayout()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (position == 0) {
                binding.horizontalScrollView.fullScroll(View.FOCUS_LEFT)
            } else if (position == binding.llAddDynamicLayout.childCount - 1) {
                binding.horizontalScrollView.fullScroll(View.FOCUS_RIGHT)
            }
        }, 170)

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                isAnimationInProgress = false
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationRepeat(p0: Animator?) {}
        })

        animator.start()
    }

}