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
        private const val DEFAULT_DURATION = 3000F
        private const val DEFAULT_START_RADIUS = 50.0
        private const val DEFAULT_FIRST_CIRCLE_DELAY = 0.00F
        private const val DEFAULT_SECOND_CIRCLE_DELAY = 0.1F
        private const val DEFAULT_THIRD_CIRCLE_DELAY = 0.2F
        private const val DEFAULT_FIRST_CIRCLE_COLOR = R.color.primaryColor
        private const val DEFAULT_SECOND_CIRCLE_COLOR = R.color.primaryDarkColor
        private const val DEFAULT_THIRD_COLOR = R.color.secondaryDarkColor
    }

    private var duration = DEFAULT_DURATION
    private var startRadius = DEFAULT_START_RADIUS
    private var firstCircleDelay = DEFAULT_FIRST_CIRCLE_DELAY
    private var secondCircleDelay = DEFAULT_SECOND_CIRCLE_DELAY
    private var thirdCircleDelay = DEFAULT_THIRD_CIRCLE_DELAY
    private var firstCircleColor: Int = 0
    private var secondCircleColor: Int = 0
    private var thirdCircleColor: Int = 0

    constructor(context: Context?): super(context) {
        init(null)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ): super(context, attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.GlowingCircleView, 0, 0)
        duration = typedArray.getFloat(R.styleable.GlowingCircleView_duration, DEFAULT_DURATION)
        startRadius =
            typedArray.getDimensionPixelSize(
                R.styleable.GlowingCircleView_startRadius,
                DEFAULT_START_RADIUS.toInt()
            ).toDouble()
        firstCircleDelay = typedArray.getFloat(
            R.styleable.GlowingCircleView_firstCircleDelay,
            DEFAULT_FIRST_CIRCLE_DELAY
        )
        secondCircleDelay = typedArray.getFloat(
            R.styleable.GlowingCircleView_secondCircleDelay,
            DEFAULT_SECOND_CIRCLE_DELAY
        )
        thirdCircleDelay = typedArray.getFloat(
            R.styleable.GlowingCircleView_thirdCircleDelay,
            DEFAULT_THIRD_CIRCLE_DELAY
        )
        firstCircleColor = typedArray.getColor(
            R.styleable.GlowingCircleView_firstCircleColor,
            ContextCompat.getColor(context, DEFAULT_FIRST_CIRCLE_COLOR)
        )
        secondCircleColor = typedArray.getColor(
            R.styleable.GlowingCircleView_secondCircleColor,
            ContextCompat.getColor(context, DEFAULT_SECOND_CIRCLE_COLOR)
        )
        thirdCircleColor = typedArray.getColor(
            R.styleable.GlowingCircleView_thirdCircleColor,
            ContextCompat.getColor(context, DEFAULT_THIRD_COLOR)
        )
        typedArray.recycle()
        circleList = arrayListOf(
            Circle(
                radius = startRadius,
                color = firstCircleColor,
                delay = firstCircleDelay
            ),
            Circle(
                radius = startRadius,
                color = secondCircleColor,
                delay = secondCircleDelay
            ),
            Circle(
                radius = startRadius,
                color = thirdCircleColor,
                delay = thirdCircleDelay
            )
        )
    }

    fun startCircleAnimation() {
        val animation = CircleRadiusAnimation()
        animation.duration = duration.toLong()
        animation.repeatCount = Animation.INFINITE
        startAnimation(animation)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        circleList.forEach {
            canvas.drawCircle(
                width / 2.toFloat(),
                height / 2.toFloat(),
                it.radius.toFloat(),
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
                it.radius = startRadius
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
                val currentRadius = width * time + startRadius
                val maxRadius = width / 2
                if (time > 0 && currentRadius > maxRadius) {
                    it.radius = maxRadius.toDouble()
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
        var radius: Double,
        private val color: Int,
        val delay: Float
    ) {
        val paint = Paint()

        init {
            paint.isAntiAlias = true
            paint.style = Paint.Style.FILL
            paint.color = color
        }
    }
}