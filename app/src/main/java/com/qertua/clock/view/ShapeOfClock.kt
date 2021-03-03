package com.qertua.clock.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.qertua.clock.R
import com.qertua.clock.utils.UiUtils.dp

class ShapeOfClock  @JvmOverloads constructor(
    context: Context,
    private val attributeSet: AttributeSet? = null,
    defStyle: Int = 0
): View(context,attributeSet,defStyle) {

    private val paint: Paint = Paint()
        .apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    private val capsuleWidth = 5f.dp()
    private val capsuleHeight = 12f.dp()
    private val radius = 7f.dp()

    init {
        initAttrs()
        background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.bg_capsule_btn_borderless,
            context.theme
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width/2f
        val centerY = height/2f
        val left = centerX - (capsuleWidth/2)
        val right = centerX + (capsuleWidth/2)
        val top = centerY - (capsuleHeight/2)
        val bottom = centerY + (capsuleHeight/2)

        canvas.drawRoundRect(left,top,right,bottom,radius,radius,paint)
    }
    private fun initAttrs() {
//        context.withStyledAttributes(attributeSet, R.styleable.CapsuleView) {
//            val priorityValue = getInt(R.styleable.CapsuleView_cv_priority, Priority.UNKNOWN.ordinal)
//            priority = Priority.values()[priorityValue]
//        }
    }
}