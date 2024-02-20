package com.mytablayout.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.mytablayout.R

class DynamicPagerAdapter(private val layoutCount: Int) : PagerAdapter() {

    override fun getCount(): Int {
        return layoutCount
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val dummyView = inflater.inflate(R.layout.dummy_textview, container, false)
        container.addView(dummyView)
        return dummyView
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

}