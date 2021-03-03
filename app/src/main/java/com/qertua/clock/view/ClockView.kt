package com.qertua.clock.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.red
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import com.qertua.clock.R
import com.qertua.clock.TimeData
import com.qertua.clock.utils.UiUtils.dp
import java.lang.Integer.max
import java.text.FieldPosition
import kotlin.math.cos
import kotlin.math.sin


class ClockView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    var time: TimeData = TimeData(0,0,0)
        set(value) {
            field = value
            invalidate()
        }

    private val capsuleWidth = 3f.dp()
    private val capsuleSecondsHeight = 80f.dp()
    private val capsuleMinutsHeight = 60f.dp()
    private val capsuleHoursHeight = 50f.dp()
    private val radius = 7f.dp()

//    private val hours: ImageView
//    private val minutes: ImageView
//    private val secunds: ImageView
    private val ignoreTag = context.resources.getString(R.string.ignore_tag)

    private val paint: Paint = Paint()
        .apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }

    init {
        View.inflate(context, R.layout.layout_color_choice, this)
        background = ContextCompat.getDrawable(context, R.drawable.bg_capsule_btn_shape)
        elevation = 8f.dp()
//        hours = findViewById(R.id.hours)
//        minutes = findViewById(R.id.minuts)
//        secunds = findViewById(R.id.secunds)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val size = max(width, height)
        setMeasuredDimension(size, size)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        children.filter { it.tag != ignoreTag }
            .forEachIndexed { index, view ->
                view.updateLayoutParams<LayoutParams> {
                    gravity = Gravity.CENTER
                }
                place(view, index, (width.times(0.3f)).toInt())
            }
//        placeTime(hours,time.hourse,(width.times(0.5f)).toInt())
//        placeTime(minutes,time.minutes,width.times(0.5f).toInt())
//        placeTime(secunds,time.secunds,width.times(0.5f).toInt())
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        val angleDeg = (time.secunds * 6.0)
//        val angleRad = Math.toRadians(angleDeg)

        val centerX = width/2f
        val centerY = height/2f
        val left = (centerX - (capsuleWidth/2))
        val right = centerX + (capsuleWidth/2)
        val top = (centerY - (capsuleSecondsHeight))
        val bottom = centerY
        canvas.save()
        canvas.rotate(angleDeg.toFloat(),centerX,centerY)
        paint.color = context.getColor(R.color.red)
        canvas.drawRoundRect(left,top,right,bottom,radius,radius,paint)
//restore canvas
        canvas.restore()
        val topMinutes = (centerY - (capsuleMinutsHeight))
        canvas.save()
        val angleDegMinutes =  (time.minutes * 6.0)
        canvas.rotate(angleDegMinutes.toFloat(),centerX,centerY)
        paint.color = context.getColor(R.color.purple_700)
        canvas.drawRoundRect(left,topMinutes,right,bottom,radius,radius,paint)
        canvas.restore()

        val topHours = (centerY - (capsuleHoursHeight))
        canvas.save()
        val angleDegHours =  (time.hourse * 30.0)
        canvas.rotate(angleDegHours.toFloat(),centerX,centerY)
        paint.color = context.getColor(R.color.black)
        canvas.drawRoundRect(left,topHours,right,bottom,radius,radius,paint)
        canvas.restore()

    }

    private fun placeTime(view: View,time:Int, radius: Int){

        view.rotation = time.times(6f)
        val angleDeg = 270 + (time * 6.0)

        val angleRad = Math.toRadians(angleDeg)
        val x = (radius+view.height/2f)
        val y = (radius+view.height/2f)
        view.translationX = radius.toFloat()
        view.translationY = radius.toFloat()
    }

    private fun place(child: View, position: Int, radius: Int) {
        val angleDeg = 270 + (position * 45.0)

        val angleRad = Math.toRadians(angleDeg)
        val x = radius.times(cos(angleRad).toFloat())
        val y = radius.times(sin(angleRad).toFloat())

        child.rotation = position.times(45f)
        child.translationX = x
        child.translationY = y
    }
}