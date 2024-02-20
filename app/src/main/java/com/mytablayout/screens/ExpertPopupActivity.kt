package com.mytablayout.screens

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mytablayout.R
import com.mytablayout.adaptors.BouncingPagerAdapter
import com.mytablayout.databinding.ActivityExpertPopupBinding
import com.mytablayout.databinding.BounceItemViewBinding
import java.util.Timer
import java.util.TimerTask

class ExpertPopupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpertPopupBinding

    private lateinit var autoScrollTimer: Timer

    private var adapter: BouncingPagerAdapter? = null

    private var currentChildIndex = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpertPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {

//        val rankDetail = RankDetail(R.drawable.expert,"Expert", "2,349 xp to next rank", "8 Missions available today")

//        val viewpagerDataList = ArrayList<VerticalRankPriceDetails>()
//        viewpagerDataList.add(VerticalRankPriceDetails("Rp1.250.000", R.drawable.wallet_icon, "Balance"))
//        viewpagerDataList.add(VerticalRankPriceDetails("Rp1.350.000", R.drawable.usage_icon, "Usage"))
//        viewpagerDataList.add(VerticalRankPriceDetails("Rp1.350.000", R.drawable.usage_icon, "Usage"))
//        viewpagerDataList.add(VerticalRankPriceDetails("Rp1.350.000", R.drawable.usage_icon, "Usage"))

//        binding.customRankPopupControl.setData(viewpagerDataList, rankDetail)

        autoScrollTimer = Timer()
        autoScrollTimer.scheduleAtFixedRate(AutoScrollTask(), 4000, 3000)

//        adapter = BouncingPagerAdapter()

//        binding.viewPager.offscreenPageLimit = 1
//        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        binding.viewPager.adapter = adapter

//        binding.horizontalScrollView.setOnTouchListener { _, _ -> true }

        // createDynamicViews(6)

    }

    private fun createDynamicViews(count: Int) {

//        binding.llAddDynamicLayout.removeAllViews()

        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        for (i in 0 until count) {
            val dynamicBinding = BounceItemViewBinding.inflate(LayoutInflater.from(this))

            val layoutParams = LinearLayout.LayoutParams(screenWidth, screenHeight)
            dynamicBinding.root.layoutParams = layoutParams

//          binding.llAddDynamicLayout.addView(dynamicBinding.root)

        }

    }

//    private fun bounceAnimation(imageView: View) {
//        val keyframe1 = Keyframe.ofFloat(0f, 0f)       // Center of the screen
//        val keyframe2 = Keyframe.ofFloat(0.2f, -400f)  // Move left
//        val keyframe3 = Keyframe.ofFloat(0.4f, -100f)  // Move slightly left
//        val keyframe4 = Keyframe.ofFloat(0.6f, 150f)   // Move slightly right
//        val keyframe5 = Keyframe.ofFloat(0.8f, -50f)   // Move slightly left
//        val keyframe6 = Keyframe.ofFloat(1f, 0f)       // Move back to center
//
//        val propertyValuesHolder = PropertyValuesHolder.ofKeyframe(
//            "translationX",
//            keyframe1,
//            keyframe2,
//            keyframe3,
//            keyframe4,
//            keyframe5,
//            keyframe6
//        )
//
//        val animator = ObjectAnimator.ofPropertyValuesHolder(imageView, propertyValuesHolder)
//        animator.interpolator = LinearInterpolator() // You can experiment with different interpolators
//        animator.duration = 500 // Adjusted duration for smoother animation
//        animator.startDelay = 0
//        animator.start()
//    }

    private fun bounceAnimation(imageView: View) {

        val keyframe1 = Keyframe.ofFloat(0f, -300f)
        val keyframe2 = Keyframe.ofFloat(0.1f, 150f)
        val keyframe3 = Keyframe.ofFloat(0.2f, -100f)
        val keyframe4 = Keyframe.ofFloat(0.3f, 100f)
        val keyframe5 = Keyframe.ofFloat(0.4f, -50f)
        val keyframe6 = Keyframe.ofFloat(0.5f, 50f)
        val keyframe7 = Keyframe.ofFloat(0.6f, -25f)
        val keyframe8 = Keyframe.ofFloat(0.7f, 25f)
        val keyframe9 = Keyframe.ofFloat(0.8f, -10f)
        val keyframe10 = Keyframe.ofFloat(0.9f, 10f)
        val keyframe11 = Keyframe.ofFloat(1f, 0f)

        val propertyValuesHolder = PropertyValuesHolder.ofKeyframe(
            "translationX",
            keyframe1,
            keyframe2,
            keyframe3,
            keyframe4,
            keyframe5,
            keyframe6,
            keyframe7,
            keyframe8,
            keyframe9,
            keyframe10,
            keyframe11
        )

        val animator = ObjectAnimator.ofPropertyValuesHolder(imageView, propertyValuesHolder)
        animator.interpolator = FastOutLinearInInterpolator()
        animator.duration = 1400
        animator.startDelay = 0
        animator.start()

    }

    //        val keyframe1 = Keyframe.ofFloat(0f, -300f)
//        val keyframe2 = Keyframe.ofFloat(0.2f, 200f)
//        val keyframe3 = Keyframe.ofFloat(0.4f, -100f)
//        val keyframe4 = Keyframe.ofFloat(0.6f, 100f)
//        val keyframe5 = Keyframe.ofFloat(0.8f, -50f)
//        val keyframe7 = Keyframe.ofFloat(1f, 0f)
//
//        val propertyValuesHolder = PropertyValuesHolder.ofKeyframe(
//            "translationX",
//            keyframe1,
//            keyframe2,
//            keyframe3,
//            keyframe4,
//            keyframe5,
//            keyframe7
//        )
//
//        val animator = ObjectAnimator.ofPropertyValuesHolder(imageView, propertyValuesHolder)
//        animator.interpolator = FastOutLinearInInterpolator()
//        animator.duration = 1000
//        animator.startDelay = 0
//        animator.start()

//        val keyframe1 = Keyframe.ofFloat(0f, 0f)
//        val keyframe2 = Keyframe.ofFloat(0.1f, -400f)
//        val keyframe3 = Keyframe.ofFloat(0.2f, 300f)
//        val keyframe4 = Keyframe.ofFloat(0.3f, -200f)
//        val keyframe5 = Keyframe.ofFloat(0.4f, 200f)
//        val keyframe6 = Keyframe.ofFloat(0.5f, -100f)
//        val keyframe7 = Keyframe.ofFloat(0.6f, 100f)
//        val keyframe8 = Keyframe.ofFloat(0.7f, -50f)
//        val keyframe9 = Keyframe.ofFloat(0.8f, 50f)
//        val keyframe10 = Keyframe.ofFloat(0.9f, -25f)
//        val keyframe11 = Keyframe.ofFloat(0.9f, 25f)
//        val keyframe12 = Keyframe.ofFloat(1f, 0f)
//
//        val propertyValuesHolder = PropertyValuesHolder.ofKeyframe(
//            "translationX",
//            keyframe1,
//            keyframe2,
//            keyframe3,
//            keyframe4,
//            keyframe5,
//            keyframe6,
//            keyframe7,
//            keyframe8,
//            keyframe9,
//            keyframe10,
//            keyframe11,
//            keyframe12
//        )
//
//        val animator =
//            ObjectAnimator.ofPropertyValuesHolder(imageView, propertyValuesHolder)
//        animator.interpolator = AccelerateDecelerateInterpolator()
//        animator.startDelay = 0
//        animator.duration = 800
//        animator.start()

    //        val keyframe1 = Keyframe.ofFloat(0f, -300f)
//        val keyframe2 = Keyframe.ofFloat(0.33f, 300f)
//        val keyframe3 = Keyframe.ofFloat(0.66f, -100f)
//        val keyframe4 = Keyframe.ofFloat(0.85f, 100f)
//        val keyframe5 = Keyframe.ofFloat(1f, 0f)
//
//        val propertyValuesHolder = PropertyValuesHolder.ofKeyframe(
//            "translationX",
//            keyframe1,
//            keyframe2,
//            keyframe3,
//            keyframe4,
//            keyframe5
//        )
//
//        val animator = ObjectAnimator.ofPropertyValuesHolder(imageView, propertyValuesHolder)
//        animator.interpolator = LinearInterpolator()
//        animator.startDelay = 0
//        animator.duration = 800
//        animator.start()
    inner class AutoScrollTask : TimerTask() {

        override fun run() {

            runOnUiThread {

                bounceAnimation(binding.llBounceView)

//                if (currentChildIndex >= binding.llAddDynamicLayout.childCount) {
//                    autoScrollTimer.cancel()
//                } else {
//                    val targetChild = binding.llAddDynamicLayout.getChildAt(currentChildIndex)
//
//                    val targetScrollX = targetChild.left
//
//                    val bounceView = targetChild.findViewById<LinearLayout>(R.id.llBounceView)
//                    bounceView?.let { bounceAnimation(it) }
//
//                    val animator = ValueAnimator.ofInt(binding.horizontalScrollView.scrollX, targetScrollX)
//                    animator.duration = 200L
//                    animator.addUpdateListener { animation ->
//                        val value = animation.animatedValue as Int
//                        binding.horizontalScrollView.scrollTo(value, 0)
//                    }
//                    animator.start()
//
//                    currentChildIndex++
//                }

            }

        }

    }

}

