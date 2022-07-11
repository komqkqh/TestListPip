package com.example.testlistpip

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 플레이어 커스텀 뷰
 */
class CustomPlayerLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

//    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
//        Log.d("TEST", "CustomPlayerLayout onInterceptTouchEvent() ev:[${ev.actionMasked}]")
//        return super.onInterceptTouchEvent(ev)
//    }

//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        Log.d("TEST", "CustomPlayerLayout dispatchTouchEvent() ev:[${ev.actionMasked}]")
//        return if (isMove) false else super.dispatchTouchEvent(ev)
//    }

    var isMove = false
}
