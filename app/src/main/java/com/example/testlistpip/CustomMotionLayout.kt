package com.example.testlistpip

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.motion.widget.MotionLayout

class CustomMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MotionLayout(context, attrs) {

    var isMove = true

//    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
//        Log.i("TEST", "CustomMotionLayout onInterceptTouchEvent() $isMove")
//        return if (isMove) super.onInterceptTouchEvent(event) else false
//    }
//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        Log.i("TEST", "CustomMotionLayout dispatchTouchEvent() $isMove")
//        return if (isMove) true else super.dispatchTouchEvent(ev)
//    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        Log.i("TEST", "CustomMotionLayout onTouchEvent() ${event.actionMasked}")
//        return super.onTouchEvent(event)
//    }
}
