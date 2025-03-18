package co.feip.fefu2025

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var itemHorizontalGap = 16.dp
    private var itemVerticalGap = 16.dp

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val availableWidth = MeasureSpec.getSize(widthMeasureSpec)
        var totalHeight = 0
        var currentLineWidth = 0
        var currentLineHeight = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            if (currentLineWidth + child.measuredWidth > availableWidth) {
                totalHeight += currentLineHeight + itemVerticalGap
                currentLineWidth = child.measuredWidth
                currentLineHeight = child.measuredHeight
            } else {
                currentLineWidth += child.measuredWidth + itemHorizontalGap
                currentLineHeight = maxOf(currentLineHeight, child.measuredHeight)
            }
        }
        totalHeight += currentLineHeight

        setMeasuredDimension(availableWidth, totalHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentX = 0
        var currentY = 0
        var currentLineHeight = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            if (currentX + child.measuredWidth > width) {
                currentY += currentLineHeight + itemVerticalGap
                currentX = 0
                currentLineHeight = 0
            }

            child.layout(
                currentX,
                currentY,
                currentX + child.measuredWidth,
                currentY + child.measuredHeight
            )

            currentX += child.measuredWidth + itemHorizontalGap
            currentLineHeight = maxOf(currentLineHeight, child.measuredHeight)
        }
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()
}