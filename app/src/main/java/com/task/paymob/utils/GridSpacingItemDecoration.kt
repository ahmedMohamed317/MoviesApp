package com.task.paymob.utils

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spacing: Int,
    private val extraLeftMargin: Int, // Extra left margin for left items
    private val extraRightMargin: Int // Extra right margin for right items
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        // Determine the layout direction
        val isRTL = ViewCompat.getLayoutDirection(parent) == ViewCompat.LAYOUT_DIRECTION_RTL

        // Apply extra margins for left and right items based on layout direction
        val leftMargin = if (position % 2 == 0) extraLeftMargin else spacing
        val rightMargin = if (position % 2 == 0) spacing else extraRightMargin

        // Adjust margins for RTL layout
        val adjustedLeftMargin = if (isRTL) rightMargin else leftMargin
        val adjustedRightMargin = if (isRTL) leftMargin else rightMargin

        // Apply margins to left and right
        outRect.left = adjustedLeftMargin
        outRect.right = adjustedRightMargin

        // Apply spacing to top and bottom
        outRect.top = 8
        outRect.bottom = 8

        // Adjust top margin for the first row
        if (position < 2) {
            outRect.top = 0
        }

        // Adjust bottom margin for the last row
        if (position >= itemCount - 2) {
            outRect.bottom = 0
        }
    }
}
