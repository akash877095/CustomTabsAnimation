package com.mytablayout.controls

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.mytablayout.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CustomCircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint()
    private val dotRadius = resources.getDimension(com.intuit.sdp.R.dimen._4sdp)
    private val dotSpacing = resources.getDimension(com.intuit.sdp.R.dimen._10sdp)

    private var gradientColors = intArrayOf(Color.parseColor("#ED1C24"),
        Color.parseColor("#7D83F7"))
    private var gradient: LinearGradient? = null

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val viewWidth = width - paddingLeft - paddingRight
        val viewHeight = height - paddingTop - paddingBottom

        val outerCircleRadius = (min(viewWidth, viewHeight)) / 2f

        val centerX = paddingLeft + (viewWidth) / 2f
        val centerY = paddingTop + (viewHeight) / 2f

        val circumference = 2 * Math.PI * outerCircleRadius

        val totalCircles = (circumference / (dotRadius + dotSpacing)).toInt()

        val angleStep = (2 * Math.PI) / totalCircles

        val gradientStartX = centerX - outerCircleRadius
        val gradientEndX = centerX + outerCircleRadius
        gradient = LinearGradient(
            gradientStartX, centerY, gradientEndX, centerY,
            gradientColors, null, Shader.TileMode.CLAMP
        )

        paint.shader = gradient

        for (i in 0 until totalCircles) {
            val angle = i * angleStep
            val x = centerX + outerCircleRadius * cos(angle).toFloat()
            val y = centerY + outerCircleRadius * sin(angle).toFloat()
            canvas.drawCircle(x, y, dotRadius, paint)
        }
    }


}
