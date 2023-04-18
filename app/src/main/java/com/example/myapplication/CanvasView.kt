package com.example.myapplication

import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColorInt

private const val STROKE_WIDTH = 12F


class CanvasView(context: MainActivity) : View(context) {
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private var motionTouchEventX = 0F
    private var motionTouchEventY = 0F
    private var currentX = 0F
    private var currentY = 0F
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    public fun setDrawcolor(color: String)
    {
        paint.color = Color.parseColor(color)
    }
//    drawColor = newColor

    private val paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = STROKE_WIDTH
        blendMode = BlendMode.XOR
    }



    private var path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0F, 0F, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            extraCanvas.drawPath(path, paint)
        }
        invalidate()
//        path.reset()
//        path.moveTo(motionTouchEventX, motionTouchEventY)
    }

    private fun touchUp() {
        path.reset()
    }

    public fun clearAll() {
        extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        invalidate()
    }
}