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
    private var circleList = arrayListOf<Circle>()

    companion object {
        private const val DURATION = 3000L
        private const val START_RADIUS = 50F
        private const val CIRCLE0_DELAY = 0.00
        private const val CIRCLE1_DELAY = 0.1
        private const val CIRCLE2_DELAY = 0.2
    }

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
        circleList = arrayListOf(
            Circle(
                color = ContextCompat.getColor(context, R.color.primaryColor),
                delay = CIRCLE0_DELAY
            ),
            Circle(
                color = ContextCompat.getColor(context, R.color.primaryDarkColor),
                delay = CIRCLE1_DELAY
            ),
            Circle(
                color = ContextCompat.getColor(context, R.color.secondaryDarkColor),
                delay = CIRCLE2_DELAY
            )
        )
    }

    fun startCircleAnimation() {
        val animation = CircleRadiusAnimation()
        animation.duration = DURATION
        animation.repeatCount = Animation.INFINITE
        startAnimation(animation)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        circleList.forEach {
            canvas.drawCircle(
                width / 2.toFloat(),
                height / 2.toFloat(),
                it.radius,
                it.paint
            )
        }
    }

    fun stopAnimation() {
        clearAnimation()
    }

    private inner class CircleRadiusAnimation: Animation() {
        override fun reset() {
            circleList.forEach {
                it.radius = START_RADIUS
            }
            requestLayout()
            this@GlowingCircleView.invalidate()
        }

        override fun applyTransformation(
            interpolatedTime: Float,
            transformation: Transformation
        ) {
            circleList.forEach {
                val time = if (interpolatedTime < 0.5) {
                    interpolatedTime - it.delay
                } else {
                    1 - it.delay - interpolatedTime
                }
                val currentRadius = width * time.toFloat() + START_RADIUS
                val maxRadius = width.toFloat() / 2
                if (time > 0 && currentRadius > maxRadius) {
                    it.radius = maxRadius
                } else if (time > 0) {
                    it.radius = currentRadius
                }
            }
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

    private data class Circle(
        var radius: Float = START_RADIUS,
        private val color: Int,
        val delay: Double
    ) {
        val paint = Paint()

        init {
            paint.isAntiAlias = true
            paint.style = Paint.Style.FILL
            paint.color = color
        }
    }
}