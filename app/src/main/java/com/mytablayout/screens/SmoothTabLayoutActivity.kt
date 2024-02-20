package com.mytablayout.screens

import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.mytablayout.DataModalClass
import com.mytablayout.R
import com.mytablayout.databinding.ActivitySmoothTabLayoutBinding
import com.mytablayout.utils.HorizontalTabBar.OnSlideTabClickListener


class SmoothTabLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySmoothTabLayoutBinding

    private var selectedItemPosition = 0

    private var isAlreadySelectedPosition = 0

    private lateinit var selectedRadioButton: RadioButton

    private lateinit var dataList: ArrayList<DataModalClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmoothTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        val mData: MutableList<String> = ArrayList()
        mData.add("BUS")
        mData.add("Trucks")
        mData.add("Electric Car Right")
        mData.add("Cycle")
        mData.add("Scooters")

        binding.horizontalTab.populateSliderData(mData, 0, OnSlideTabClickListener { })

        /*
        * Upper Tab Layout code
        * */
        val tab1 = binding.tabLayout.newTab()
        tab1.text = "Akash"
        binding.tabLayout.addTab(tab1)

        val tab2 = binding.tabLayout.newTab()
        tab2.text = "Dinesh"
        binding.tabLayout.addTab(tab2)

        val tab3 = binding.tabLayout.newTab()
        tab3.text = "Alok"
        binding.tabLayout.addTab(tab3)

        val tab4 = binding.tabLayout.newTab()
        tab4.text = "Chetan Bhagat"
        binding.tabLayout.addTab(tab4)

        val tab5 = binding.tabLayout.newTab()
        tab5.text = "Thakur Ji"
        binding.tabLayout.addTab(tab5)

        val tab6 = binding.tabLayout.newTab()
        tab6.text = "Gulshan Kumar Singh Chauhan"
        binding.tabLayout.addTab(tab6)

        /*
        * Bottom Tab Layout code
        * */
        val tab11 = binding.tabLayout2.newTab()
        tab11.text = "Akash Jadhav"
        binding.tabLayout2.addTab(tab11)

        val tab22 = binding.tabLayout2.newTab()
        tab22.text = "Dinesh dhakar"
        binding.tabLayout2.addTab(tab22)

        val tab33 = binding.tabLayout2.newTab()
        tab33.text = "Alok Jain"
        binding.tabLayout2.addTab(tab33)

        val tab44 = binding.tabLayout2.newTab()
        tab44.text = "Chetan Bhagat"
        binding.tabLayout2.addTab(tab44)

        val tab55 = binding.tabLayout2.newTab()
        tab55.text = "Thakur Ji"
        binding.tabLayout2.addTab(tab55)

        val tab66 = binding.tabLayout2.newTab()
        tab66.text = "Gulshan Kumar Singh Chauhan"
        binding.tabLayout2.addTab(tab66)

//        binding.tabLayout2.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                val tabPosition = tab.position
//                val tabView: TabLayout.TabView? = binding.tabLayout2.getTabAt(tabPosition)?.view
//
//                // Calculate the left and right edges of the tab's view within the TabLayout
//                val tabLeftEdge = tabView?.left ?: 0
//                val tabRightEdge = tabView?.right ?: 0
//
//                // Calculate the left and right edges of the HorizontalScrollView's visible area
//                val scrollViewLeftEdge = binding.horizontalScrollView2.scrollX
//                val scrollViewRightEdge = scrollViewLeftEdge + binding.horizontalScrollView2.width
//
//                // Define a minimum scroll offset to prevent excessive scrolling to the left
//                val minScrollOffset = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp)
//
//                // Calculate the final scroll position with a maximum of minScrollOffset
//                val finalScrollX = max(tabLeftEdge - minScrollOffset, scrollViewLeftEdge)
//
//                // Scroll the HorizontalScrollView to make the tab partially visible
//                binding.horizontalScrollView2.smoothScrollTo(finalScrollX, 0)
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//        })


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

        // for (i in 0 until dataList.size) addDynamicView(dataList[i], i)

    }

//    private fun addDynamicView(data: DataModalClass, i: Int) {
//
//        val bindingTab = SmoothTabItemBinding.inflate(LayoutInflater.from(this), null, false)
//
//        bindingTab.tabText.text = data.title
//
//        if (i == selectedItemPosition) {
//            // For the selected item
//            bindingTab.tabText.setTextColor(ContextCompat.getColor(this, R.color.white))
//            // bindingTab.tabRoot.background =
//            //   ContextCompat.getDrawable(this, R.drawable.selected_tab_back)
//        } else {
//            // For the unselected item
//            bindingTab.tabText.setTextColor(ContextCompat.getColor(this, R.color.black))
//            bindingTab.tabRoot.background = null
//        }
//
//        binding.llAddDynamicLayout.addView(bindingTab.root)
//
//        bindingTab.tabRoot.setOnClickListener {
//
//            // scrollToNextItemIfNotVisible(i)
//            // scrollToPreviousItemIfNotVisible(i)
//
//            Log.e("gfgfgfgfgfg", "is Last Item = ${i == binding.llAddDynamicLayout.childCount - 1}")
//
//            val previousSelectedItem =
//                binding.llAddDynamicLayout.getChildAt(selectedItemPosition)
//            val previousTabText = previousSelectedItem.findViewById<TextView>(R.id.tabText)
//            val previousTabRoot = previousSelectedItem.findViewById<LinearLayout>(R.id.tabRoot)
//
//            previousTabText.setTextColor(Color.BLACK)
//            previousTabRoot.background = null
//
//
//            selectedItemPosition = i
//            isAlreadySelectedPosition = selectedItemPosition
//
//            bindingTab.tabText.setTextColor(ContextCompat.getColor(this, R.color.white))
//            //  bindingTab.tabRoot.background =
//            // ContextCompat.getDrawable(this, R.drawable.selected_tab_back)
//            bindingTab.tabText.visibility = View.VISIBLE
//
//            Handler(Looper.getMainLooper()).postDelayed({
//                animateVvViewToCenter(bindingTab.tabRoot)
//            }, 2000)
//
//        }
//
//    }
//
//    private fun scrollToNextItemIfNotVisible(currentClickedIndex: Int) {
//        val nextItemIndex = currentClickedIndex + 1
//
//        if (nextItemIndex < binding.llAddDynamicLayout.childCount) {
//
//            val nextItem = binding.llAddDynamicLayout.getChildAt(nextItemIndex)
//
//            val nextItemRight = nextItem.right
//            val visibleRight =
//                binding.horizontalScrollView.scrollX + binding.horizontalScrollView.width
//
//            if (nextItemRight > visibleRight) {
//                val scrollAmount = nextItemRight - visibleRight
//
//                binding.horizontalScrollView.smoothScrollBy(scrollAmount, 0)
//            }
//
//        }
//
//    }
//
//    private fun scrollToPreviousItemIfNotVisible(currentClickedIndex: Int) {
//        val previousItemIndex = currentClickedIndex - 1
//
//        if (previousItemIndex >= 0) {
//            val previousItem = binding.llAddDynamicLayout.getChildAt(previousItemIndex)
//
//            val previousItemLeft = previousItem.left
//            val visibleLeft = binding.horizontalScrollView.scrollX
//
//            if (previousItemLeft < visibleLeft) {
//                val scrollAmount = previousItemLeft - visibleLeft
//
//                binding.horizontalScrollView.smoothScrollBy(scrollAmount, 0)
//            }
//        }
//    }
//
//    private fun animateVvViewToCenter(clickedView: View) {
//        val vvViewWidth = binding.vvView.width
//        val scrollViewWidth = binding.horizontalScrollView.width
//
//        val clickedViewX = clickedView.x
//        val clickedCenterX = clickedViewX + clickedView.width / 2
//
//        val vvViewX = clickedCenterX - vvViewWidth / 2
//
//        val scrollViewScrollX = binding.horizontalScrollView.scrollX
//
//        // Calculate the final scroll position for the HorizontalScrollView
//        val targetScrollX = vvViewX.toInt() - (scrollViewWidth - vvViewWidth) / 2
//
//        // Calculate the scroll amount for the HorizontalScrollView
//        val scrollAmount = targetScrollX - scrollViewScrollX
//
//        // Animate vvView to the target position
//        val animator = ValueAnimator.ofFloat(binding.vvView.x, vvViewX)
//        animator.duration = 300
//
//        animator.addUpdateListener { animation ->
//            val animatedValue = animation.animatedValue as Float
//            binding.vvView.x = animatedValue
//
//            // Scroll the HorizontalScrollView
//            binding.horizontalScrollView.smoothScrollBy(scrollAmount, 0)
//        }
//
//        animator.start()
//    }

}