package com.github.corentinc.notificoin.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.content.ContextCompat
import com.github.corentinc.notificoin.R

class GlowingCircleView: View {
    private val paints = arrayListOf(Paint(), Paint(), Paint())
    private val colors = IntArray(3)
    val circleRadius = FloatArray(3)

    constructor(context: Context?): super(context) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ): super(context, attrs) {
        init()
    }

    private fun init() {
        colors[0] = ContextCompat.getColor(context, R.color.secondaryDarkColor)
        colors[1] = ContextCompat.getColor(context, R.color.primaryDarkColor)
        colors[2] = ContextCompat.getColor(context, R.color.primaryColor)
        paints.forEachIndexed { index, paint ->
            paint.isAntiAlias = true
            paint.style = Paint.Style.FILL
            paint.color = colors[index]
        }
    }

    fun startCircleAnimation() {
        val animation = CircleRadiusAnimation()
        animation.duration = 3000
        animation.repeatCount = Animation.INFINITE
        startAnimation(animation)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            circleRadius[0],
            paints[0]
        )
        canvas.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            circleRadius[1],
            paints[1]
        )
        canvas.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            circleRadius[2],
            paints[2]
        )
    }

    fun stopAnimation() {
        clearAnimation()
    }

    private inner class CircleRadiusAnimation: Animation() {
        override fun reset() {
            circleRadius[0] = 0F
            circleRadius[1] = 0F
            circleRadius[2] = 0F
            requestLayout()
            this@GlowingCircleView.invalidate()
        }

        override fun applyTransformation(
            interpolatedTime: Float,
            transformation: Transformation
        ) {
            val interval = (width * 0.01 / 2).toFloat()
            if (interpolatedTime < 0.5) {
                circleRadius[0] = circleRadius[0] + interval
                if (interpolatedTime > 0.02) {
                    circleRadius[1] = circleRadius[1] + interval
                }
                if (interpolatedTime > 0.1) {
                    circleRadius[2] = circleRadius[2] + interval
                }
            } else {
                circleRadius[0] = circleRadius[0] - interval
                circleRadius[1] = circleRadius[1] - interval
                circleRadius[2] = circleRadius[2] - interval
            }
            requestLayout()
            this@GlowingCircleView.invalidate()
        }

        init {
            setAnimationListener(object: AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {
                    reset()
                }
            })
        }
    }
}