package com.leqi.scooterrecite.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


class ReciteTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {}

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
    }

    private var mLineHeight = 0F
    private var mLineColor = 0
    private var isShowUnderLine = false

    fun setunderlineStyle(lineHeight: Float, lineColor: Int) {
        mLineHeight = lineHeight
        mLineColor = lineColor
    }

    fun isShowUnderLine(enable: Boolean) {
        isShowUnderLine = enable
        invalidate()
    }

    private var mIndex = 0
    fun setWorldIndex(index: Int) {
        mIndex = index
    }

    private var mErrorNum = 0
    fun setErrorNum(num: Int) {
        mErrorNum = num
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
//                paint.color = Color.BLACK //设置颜色
//                paint.textSize = 20f
//                canvas?.drawText( mIndex.toString(),width/2.toFloat(),12F,paint)
        paint.color = Color.RED //设置颜色
        paint.textSize = 20f
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
        if (mErrorNum != 0) {
            canvas?.drawText(mErrorNum.toString(), 0F, 20F, paint)
        }
        if (isShowUnderLine) {
            val paint = Paint()
            paint.color = mLineColor //设置颜色
            paint.strokeWidth = mLineHeight //设置描边宽度
            paint.style = Paint.Style.STROKE
            canvas?.drawLine(0F, height.toFloat(), width.toFloat(), height.toFloat(), paint)
        }
    }
}