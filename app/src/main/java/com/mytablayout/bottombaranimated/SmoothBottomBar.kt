package com.mytablayout.bottombaranimated

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.FontRes
import androidx.annotation.XmlRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import com.mytablayout.R
import com.mytablayout.utils.HorizontalTabBar

class SmoothBottomBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.SmoothBottomBarStyle
) : View (context, attrs, defStyleAttr) {

    // Dynamic Variables
    private var itemWidth: Float = 0F

    private var indicatorLocation = barSideMargins

    private val rect = RectF()

    private var items = listOf<BottomBarItem>()

    @ColorInt
    private var _barBackgroundColor = Color.WHITE

    @ColorInt
    private var _barIndicatorColor = Color.parseColor(DEFAULT_INDICATOR_COLOR)

    @Dimension
    private var _barIndicatorRadius = d2p(DEFAULT_CORNER_RADIUS)

    @Dimension
    private var _barSideMargins = d2p(DEFAULT_SIDE_MARGIN)

    @Dimension
    private var _barCornerRadius = d2p(DEFAULT_BAR_CORNER_RADIUS)

    private var _barCorners = DEFAULT_BAR_CORNERS

    @Dimension
    private var _itemPadding = d2p(DEFAULT_ITEM_PADDING)

    @Dimension
    private var _itemAnimDuration = DEFAULT_ANIM_DURATION

    @Dimension
    private var _itemTextSize = d2p(DEFAULT_TEXT_SIZE)

    @ColorInt
    private var _itemTextColor = Color.BLACK

    @FontRes
    private var _itemFontFamily: Int = INVALID_RES

    @XmlRes
    private var _itemMenuRes: Int = INVALID_RES

    private var _itemActiveIndex: Int = 0

    // Core Attributes
    var barBackgroundColor: Int
        @ColorInt get() = _barBackgroundColor
        set(@ColorInt value) {
            _barBackgroundColor = value
            paintBackground.color = value
            invalidate()
        }

    var barIndicatorColor: Int
        @ColorInt get() = _barIndicatorColor
        set(@ColorInt value) {
            _barIndicatorColor = value
            paintIndicator.color = value
            invalidate()
        }

    var barIndicatorRadius: Float
        @Dimension get() = _barIndicatorRadius
        set(@Dimension value) {
            _barIndicatorRadius = value
            invalidate()
        }

    var barSideMargins: Float
        @Dimension get() = _barSideMargins
        set(@Dimension value) {
            _barSideMargins = value
            invalidate()
        }

    var barCornerRadius: Float
        @Dimension get() = _barCornerRadius
        set(@Dimension value) {
            _barCornerRadius = value
            invalidate()
        }

    var barCorners: Int
        get() = _barCorners
        set(value) {
            _barCorners = value
            invalidate()
        }

    var itemTextSize: Float
        @Dimension get() = _itemTextSize
        set(@Dimension value) {
            _itemTextSize = value
            paintText.textSize = value
            invalidate()
        }

    var itemTextColor: Int
        @ColorInt get() = _itemTextColor
        set(@ColorInt value) {
            _itemTextColor = value
            paintText.color = value
            invalidate()
        }

    var itemPadding: Float
        @Dimension get() = _itemPadding
        set(@Dimension value) {
            _itemPadding = value
            invalidate()
        }

    var itemAnimDuration: Long
        get() = _itemAnimDuration
        set(value) {
            _itemAnimDuration = value
        }

    var itemFontFamily: Int
        @FontRes get() = _itemFontFamily
        set(@FontRes value) {
            _itemFontFamily = value
            if (value != INVALID_RES) {
                paintText.typeface = ResourcesCompat.getFont(context, value)
                invalidate()
            }
        }

    var itemMenuRes: Int
        @XmlRes get() = _itemMenuRes
        set(@XmlRes value) {
            _itemMenuRes = value
            if (value != INVALID_RES) {
                items = BottomBarParser(context, value).parse()
                invalidate()
            }
        }

    var itemActiveIndex: Int
        get() = _itemActiveIndex
        set(value) {
            _itemActiveIndex = value
            applyItemActiveIndex()
        }

    // Listeners
    var onItemSelectedListener: OnItemSelectedListener? = null

    var onItemReselectedListener: OnItemReselectedListener? = null

    var onItemSelected: ((Int) -> Unit)? = null

    var onItemReselected: ((Int) -> Unit)? = null

    // Paints
    private val paintBackground = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = barIndicatorColor
    }

    private val paintIndicator = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = barIndicatorColor
    }

    private val paintText = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = itemTextColor
        textSize = itemTextSize
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    private var exploreByTouchHelper: AccessibleExploreByTouchHelper

    init {
        obtainStyledAttributes(attrs, defStyleAttr)
        exploreByTouchHelper = AccessibleExploreByTouchHelper(this, items, ::onClickAction)

        ViewCompat.setAccessibilityDelegate(this, exploreByTouchHelper)
    }

    private fun obtainStyledAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SmoothBottomBar,
            defStyleAttr,
            0
        )

        try {
            barBackgroundColor = typedArray.getColor(
                R.styleable.SmoothBottomBar_backgroundColor,
                barBackgroundColor
            )
            barIndicatorColor = typedArray.getColor(
                R.styleable.SmoothBottomBar_indicatorColor,
                barIndicatorColor
            )
            barIndicatorRadius = typedArray.getDimension(
                R.styleable.SmoothBottomBar_indicatorRadius,
                barIndicatorRadius
            )
            barSideMargins = typedArray.getDimension(
                R.styleable.SmoothBottomBar_sideMargins,
                barSideMargins
            )
            barCornerRadius = typedArray.getDimension(
                R.styleable.SmoothBottomBar_cornerRadius,
                barCornerRadius
            )
            barCorners = typedArray.getInteger(
                R.styleable.SmoothBottomBar_corners,
                barCorners
            )
            itemPadding = typedArray.getDimension(
                R.styleable.SmoothBottomBar_itemPadding,
                itemPadding
            )
            itemTextColor = typedArray.getColor(
                R.styleable.SmoothBottomBar_textColor,
                itemTextColor
            )
            itemTextSize = typedArray.getDimension(
                R.styleable.SmoothBottomBar_textSize,
                itemTextSize
            )
            itemActiveIndex = typedArray.getInt(
                R.styleable.SmoothBottomBar_activeItem,
                itemActiveIndex
            )
            itemFontFamily = typedArray.getResourceId(
                R.styleable.SmoothBottomBar_itemFontFamily,
                itemFontFamily
            )
            itemAnimDuration = typedArray.getInt(
                R.styleable.SmoothBottomBar_duration,
                itemAnimDuration.toInt()
            ).toLong()
            itemMenuRes = typedArray.getResourceId(
                R.styleable.SmoothBottomBar_menu,
                itemMenuRes
            )
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }
    }

    private fun d2p(dp: Float): Float {
        return dp * resources.displayMetrics.density
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val totalAvailableWidth = width - (barSideMargins * 2)
        itemWidth = totalAvailableWidth / items.size

        // Calculate the actual item width to avoid overlapping
        if (itemWidth * items.size > totalAvailableWidth) {
            itemWidth = totalAvailableWidth / items.size
        }

        var lastX = barSideMargins

        // reverse items layout order if layout direction is RTL
        val itemsToLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
            && layoutDirection == LAYOUT_DIRECTION_RTL
        ) items.reversed() else items

        for (item in itemsToLayout) {
            item.rect = RectF(lastX, 0f, itemWidth + lastX, height.toFloat())
            lastX += itemWidth
        }

        applyItemActiveIndex()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background
        if (barCornerRadius > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(
                0f, 0f,
                width.toFloat(),
                height.toFloat(),
                minOf(barCornerRadius.toFloat(), height.toFloat() / 2),
                minOf(barCornerRadius.toFloat(), height.toFloat() / 2),
                paintBackground
            )

            if (barCorners != ALL_CORNERS) {
                // Draw corner rectangles (removed for brevity)
            }

        } else {
            canvas.drawRect(
                0f, 0f,
                width.toFloat(),
                height.toFloat(),
                paintBackground
            )
        }

        // Draw indicator
        rect.left = indicatorLocation
        rect.top = items[itemActiveIndex].rect.centerY() - itemPadding
        rect.right = indicatorLocation + itemWidth
        rect.bottom = items[itemActiveIndex].rect.centerY() + itemPadding

        canvas.drawRoundRect(
            rect,
            barIndicatorRadius,
            barIndicatorRadius,
            paintIndicator
        )

        val textHeight = (paintText.descent() + paintText.ascent()) / 2

        for ((index, item) in items.withIndex()) {
            // Calculate the text position based on the item's rect
            val textX = item.rect.centerX()
            val textY = item.rect.centerY() - textHeight

            this.isClickable = false

            // Draw the item title
            canvas.drawText(item.title, textX, textY, paintText)
        }

    }

    /**
     * Handle item clicks
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                return true
            }

            MotionEvent.ACTION_UP -> {
                for ((i, item) in items.withIndex()) {
                    if (item.rect.contains(event.x, event.y)) {
                        onClickAction(i)
                        break
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun dispatchHoverEvent(event: MotionEvent): Boolean {
        return exploreByTouchHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event)
    }

    private fun onClickAction(viewId: Int) {
        exploreByTouchHelper.invalidateVirtualView(viewId)
        if (viewId != itemActiveIndex) {
            itemActiveIndex = viewId
            onItemSelected?.invoke(viewId)
            onItemSelectedListener?.onItemSelect(viewId)
        } else {
            onItemReselected?.invoke(viewId)
            onItemReselectedListener?.onItemReselect(viewId)
        }
        exploreByTouchHelper.sendEventForVirtualView(
            viewId,
            AccessibilityEvent.TYPE_VIEW_CLICKED
        )
    }

    private fun applyItemActiveIndex() {
        if (items.isNotEmpty()) {
            for ((index, item) in items.withIndex()) {
                if (index == itemActiveIndex) {
                    animateAlpha(item, OPAQUE)
                } else {
                    animateAlpha(item, TRANSPARENT)
                }
            }

            ValueAnimator.ofFloat(
                indicatorLocation,
                items[itemActiveIndex].rect.left
            ).apply {
                duration = itemAnimDuration
                interpolator = DecelerateInterpolator()
                addUpdateListener { animation ->
                    indicatorLocation = animation.animatedValue as Float
                }
                start()
            }

        }
    }

    private fun animateAlpha(item: BottomBarItem, to: Int) {
        ValueAnimator.ofInt(item.alpha, to).apply {
            duration = itemAnimDuration
            addUpdateListener {
                val value = it.animatedValue as Int
                item.alpha = value
                invalidate()
            }
            start()
        }
    }

    fun setupWithNavController(menu: Menu, navController: NavController) {
        NavigationComponentHelper.setupWithNavController(menu, this, navController)
    }

    /**
     * Created by Vladislav Perevedentsev on 29.07.2020.
     *
     * Just call [SmoothBottomBar.setOnItemSelectedListener] to override [onItemSelectedListener]
     *
     * @sample
     * setOnItemSelectedListener { position ->
     *     //TODO: Something
     * }
     */
    fun setOnItemSelectedListener(listener: (position: Int) -> Unit) {
        onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelect(pos: Int): Boolean {
                listener.invoke(pos)
                return true
            }
        }
    }

    /**
     * Created by Vladislav Perevedentsev on 29.07.2020.
     *
     * Just call [SmoothBottomBar.setOnItemReselectedListener] to override [onItemReselectedListener]
     *
     * @sample
     * setOnItemReselectedListener { position ->
     *     //TODO: Something
     * }
     */
    fun setOnItemReselectedListener(listener: (position: Int) -> Unit) {
        onItemReselectedListener = object : OnItemReselectedListener {
            override fun onItemReselect(pos: Int) {
                listener.invoke(pos)
            }
        }
    }

    companion object {
        private const val INVALID_RES = -1
        private const val DEFAULT_INDICATOR_COLOR = "#2DFFFFFF"
        private const val DEFAULT_TINT = "#C8FFFFFF"

        // corner flags
        private const val NO_CORNERS = 0;
        private const val TOP_LEFT_CORNER = 1;
        private const val TOP_RIGHT_CORNER = 2;
        private const val BOTTOM_RIGHT_CORNER = 4;
        private const val BOTTOM_LEFT_CORNER = 8;
        private const val ALL_CORNERS = 15;

        private const val DEFAULT_SIDE_MARGIN = 10f
        private const val DEFAULT_ITEM_PADDING = 10f
        private const val DEFAULT_ANIM_DURATION = 200L
        private const val DEFAULT_ICON_SIZE = 18F
        private const val DEFAULT_ICON_MARGIN = 4F
        private const val DEFAULT_TEXT_SIZE = 11F
        private const val DEFAULT_CORNER_RADIUS = 20F
        private const val DEFAULT_BAR_CORNER_RADIUS = 0F
        private const val DEFAULT_BAR_CORNERS = TOP_LEFT_CORNER or TOP_RIGHT_CORNER

        private const val OPAQUE = 255
        private const val TRANSPARENT = 0
    }
}
