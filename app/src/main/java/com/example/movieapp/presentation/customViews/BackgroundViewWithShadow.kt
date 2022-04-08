package com.example.movieapp.presentation.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.movieapp.R

@SuppressLint("NewApi")
class BackgroundViewWithShadow @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(ctx, attrs, defStyleAttr, defStyleRes) {
    private val paint = Paint()
    private val path = Path()
    private val layerPaint = Paint()

    private var shadowRadius = 4f
    private var offSetX = 6f
    private var offSetY = 6f
    private var topLeftCornerRadius = 30f
    private var topRightCornerRadius = 30f
    private var bottomLeftCornerRadius = 30f
    private var bottomRightCornerRadius = 30f
    private var viewHeight = 600f
    private var viewWidth = 600f
    private val startPosition
        get() = shadowRadius

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.BackgroundViewWithShadow, 0, 0).apply {
            offSetX = getDimension(R.styleable.BackgroundViewWithShadow_offsetX, 6f)
            offSetY = getDimension(R.styleable.BackgroundViewWithShadow_offsetY, 6f)
            topLeftCornerRadius = getDimension(R.styleable.BackgroundViewWithShadow_topLeftCornerRadius, 0f)
            topRightCornerRadius = getDimension(R.styleable.BackgroundViewWithShadow_topRightCornerRadius, 0f)
            bottomLeftCornerRadius = getDimension(R.styleable.BackgroundViewWithShadow_bottomLeftCornerRadius, 0f)
            bottomRightCornerRadius = getDimension(R.styleable.BackgroundViewWithShadow_bottomRightCornerRadius, 0f)
            shadowRadius = getDimension(R.styleable.BackgroundViewWithShadow_shadowRadius, 4f)
            val cornerRadius = getDimension(R.styleable.BackgroundViewWithShadow_cornerRadius, 0f)
            setCornerRadius(cornerRadius)
        }

        paint.color = Color.WHITE
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        setLayerType(LAYER_TYPE_SOFTWARE, layerPaint)
        setLayerPaint(layerPaint)
        paint.setShadowLayer(shadowRadius, offSetX, offSetY, ctx.getColor(R.color.shadow_color))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewHeight = measuredHeight.toFloat()
        viewWidth = measuredWidth.toFloat()
        viewHeight -= shadowRadius + offSetY
        viewWidth -= shadowRadius + offSetX
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.moveTo(startPosition, startPosition)
        path.lineTo(startPosition, viewHeight)

        path.arcTo(
            startPosition,
            viewHeight - 2 * bottomLeftCornerRadius,
            startPosition + 2 * bottomLeftCornerRadius,
            viewHeight,
            -180f,
            -90f,
            false
        )

        path.lineTo(viewWidth, viewHeight)

        path.arcTo(
            viewWidth - 2 * bottomRightCornerRadius,
            viewHeight - 2 * bottomRightCornerRadius,
            viewWidth,
            viewHeight,
            90f,
            -90f,
            false
        )

        path.lineTo(viewWidth, startPosition + 2 * topRightCornerRadius)

        path.arcTo(
            viewWidth - 2 * topRightCornerRadius,
            startPosition,
            viewWidth,
            startPosition + 2 * topRightCornerRadius,
            0f,
            -90f,
            false
        )

        path.lineTo(startPosition + 2 * topLeftCornerRadius, startPosition)

        path.arcTo(
            startPosition,
            startPosition,
            startPosition + 2 * topLeftCornerRadius,
            startPosition + 2 * topLeftCornerRadius,
            -90f,
            -90f,
            false
        )

        canvas?.drawPath(path, paint)
    }

    private fun setCornerRadius(radius: Float){
        topLeftCornerRadius = radius
        topRightCornerRadius = radius
        bottomLeftCornerRadius = radius
        bottomRightCornerRadius = radius
    }

}