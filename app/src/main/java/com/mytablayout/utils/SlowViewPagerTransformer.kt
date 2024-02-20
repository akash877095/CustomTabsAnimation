package com.mytablayout.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class SlowViewPagerTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width
        if (position < -1) {  // Page to the left of the screen
            page.alpha = 0f
        } else if (position <= 1) {  // Page on the screen or entering the screen
            // Adjust scale and alpha for a slower transition
            val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
            val alphaFactor = Math.max(MIN_ALPHA, 1 - Math.abs(position))

            // Apply transformation to the view
            page.alpha = alphaFactor
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
        } else {  // Page to the right of the screen
            page.alpha = 0f
        }
    }

    companion object {
        private const val MIN_SCALE = 0.75f
        private const val MIN_ALPHA = 0.5f
    }
}





