package com.github.anastr.rxlab.objects.emits

import android.graphics.Canvas
import com.github.anastr.rxlab.util.Point
import com.github.anastr.rxlab.util.dpToPx

/**
 * Created by Anas Altair on 7/8/2020.
 */
class ListEmit(leftTopPoint: Point, vararg ballEmits: BallEmit): BallEmit(leftTopPoint) {

    init {
        super.value = "{${ballEmits.map { it.value }.reduce { acc, emitValue -> "$acc, $emitValue" }}}"
        ballEmits.firstOrNull()?.let { super.color = it.color }
    }

    override fun drawContent(canvas: Canvas) {
        canvas.drawRoundRect(rect, dpToPx(5f), dpToPx(5f), emitPaint)
        canvas.drawText(value, rect.centerX(), rect.centerY() + valueTextHeight*.5f, valuePaint)
    }

}