package com.example.testlistpip

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.example.testlistpip.databinding.FragmentTestBinding
import kotlin.math.abs

/**
 * 테스트 화면
 */
class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding

    var isMove = false // 애니메이션 처리중일경우
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestBinding.inflate(layoutInflater)
        return binding.root
    }

    private var motionProgress = 0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 모션레이아웃 리스너
        binding.includeMotion.motionLayout.setTransitionListener(object :
                MotionLayout.TransitionListener {
                var motionStartId = 0
                var motionEndId = 0

                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                    motionStartId = startId
                    motionEndId = endId
                    Log.d("TEST", "onTransitionStarted() startId:[$startId], endId:[$endId]")
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                    motionProgress = progress
//                    Log.d(
//                        "TEST",
//                        "onTransitionChange() startId:[$startId], endId:[$endId], progress:[$progress]"
//                    )
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    Log.d(
                        "TEST",
                        "onTransitionCompleted() currentId:[$currentId] isMove:{$isMove}"
                    )

                    if (!isMove) {
                        binding.includeMotion.motionLayout.isEnabled =
                            motionEndId != binding.includeMotion.motionLayout.currentState
                        Log.d(
                            "TEST",
                            "onTransitionCompleted() isEnabled:::${binding.includeMotion.motionLayout.isEnabled} endConstraintSetId:[$motionEndId]"
                        )

                        // 복귀는 하네 ㅋㅋ...
                        binding.includeMotion.topImageContainer.let { v ->
                            Log.d(
                                "TEST",
                                "onTransitionCompleted topImageContainer position (${v.x}, ${v.y})"
                            )
                            v.x = 0f
                            v.y = 0f
                        }
                    }
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                    Log.d(
                        "TEST",
                        "onTransitionTrigger() triggerId$triggerId], positive:[$positive], progress:[$progress]"
                    )
                }
            })

        binding.includeMotion.constraintLayout.setOnClickListener {
            // 모션 레이아웃의 터치를 막는다.
        }

        binding.includeMotion.topImageContainer.let { v ->
            Log.d("TEST", "topImageContainer position (${v.x}, ${v.y})")
        }

        binding.includeMotion.motionLayout.getConstraintSet(R.id.end).apply {
            getConstraint(R.id.id_fragment_layout).apply {
                Log.d("TEST", "id_fragment_layout position (${view.x}, ${view.y})")
            }
        }

        /**
         * 상단 뷰의 터치 리스너 동작
         */
        binding.includeMotion.topImageContainer.setOnTouchListener(object : View.OnTouchListener {

            var touchX = 0f
            var touchY = 0f

            override fun onTouch(v: View, mv: MotionEvent): Boolean {
                //                Log.d(
                //                    "TEST",
                //                    "topImageContainer setOnTouchListener mv:[${mv.actionMasked}], ${binding.includeMotion.motionLayout.isEnabled}, checkIsEndState():[${checkIsEndState()}]"
                //                )

                if (mv.actionMasked == MotionEvent.ACTION_DOWN) {
                    Log.d("TEST", "topImageContainer setOnTouchListener ACTION_DOWN")
                    touchX = mv.rawX
                    touchY = mv.rawY
                    isMove = false
                    Log.d("TEST", "ACTION_DOWN position (${v.x}, ${v.y})")
                } else if (mv.actionMasked == MotionEvent.ACTION_UP) {
                    Log.d(
                        "TEST",
                        "topImageContainer setOnTouchListener ACTION_UP ($isMove, ${checkIsEndState()}, ${getMotionEnabled()})"
                    )
                    // 드래그 중일경우 하단에 도착하면 터치 상태를 취소해야됨. (터치와 드래그 분기처리)
                    if (!isMove && checkIsEndState() && !getMotionEnabled()) {
                        Log.d("TEST", "최대화 애니메이션 동작!!!")
                        binding.includeMotion.motionLayout.isEnabled = true
                        binding.includeMotion.motionLayout.transitionToStart()
                        Log.d("TEST", "ACTION_UP position (${v.x}, ${v.y})")
                    } else if (isMove && checkIsEndState()) {
                        binding.includeMotion.motionLayout.isEnabled = false
                        // onTransitionCompleted 가 되지 않으면 백그라운드 터치가 안됨.
                    }
                    isMove = false
                } else if (mv.actionMasked == MotionEvent.ACTION_MOVE) {
                    var moveX = abs(touchX - mv.rawX)
                    var moveY = abs(touchY - mv.rawY)
                    Log.d(
                        "TEST",
                        "topImageContainer setOnTouchListener ACTION_MOVE x($moveX = $touchX - ${mv.rawX}), y($moveY = $touchY - ${mv.rawY})"
                    )
                    if (!isMove && moveX > 30 || moveY > 30) {
                        Log.d("TEST", "topImageContainer setOnTouchListener ACTION_MOVE !!!!!!")
                        isMove = true
                    }
                }

                moveView(v, mv)

                if (getMotionEnabled()) {
                    binding.includeMotion.motionLayout.onTouchEvent(mv)
                }

                return true
            }
        })

        // 터치리스너인데 true 하면 activity 터치가 안먹힌다.
//        binding.includeMotion.motionLayout.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(view: View, mv: MotionEvent): Boolean {
//
//                if(mv.actionMasked == MotionEvent.ACTION_UP) {
//                    binding.includeMotion.motionLayout.getTransition(R.id.motion_transition).endConstraintSetId
//
// //                    Log.d("TEST", "setOnTouchListener() isEnabled:::$isEnabled")
//                }
//
//                return checkIsEndState()
//            }
//        })
    }

    var moveX = 0f
    var moveY = 0f

    /**
     * 화면 이동시켜주는 터치리스너 추가 함수
     */
    private fun moveView(v: View, event: MotionEvent): Boolean {

        var parentView: View = v.parent as View

//        Log.d(
//            "TEST",
//            "touch [${parentView.width}, ${parentView.height}] (x, y):[${v.x}, ${v.y}]"
//        )
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                moveX = v.x - event.rawX
                moveY = v.y - event.rawY
                Log.d(
                    "TEST",
                    "touch ACTION_DOWN [$moveX, $moveY], event:[${event.rawX}, ${event.rawY}]"
                )
            }

            MotionEvent.ACTION_MOVE -> {
                if (!getMotionEnabled() && checkIsEndState()) {
                    v.animate()
                        .x(event.rawX + moveX)
                        .y(event.rawY + moveY)
                        .setDuration(0)
                        .start()
                }
            }
            MotionEvent.ACTION_UP -> {
                Log.d(
                    "TEST",
                    "touch ACTION_UP (${getMotionEnabled()}, ${checkIsEndState()}))"
                )
                if (!getMotionEnabled() && checkIsEndState()) {
                    actionUpMove(parentView, v, v.x, v.y)
                }
            }
        }

        return true
    }

    /**
     * 4등분 화면으로 이동시켜주는 애니메이션 처리
     */
    fun actionUpMove(parent: View, view: View, x: Float, y: Float) {
        // 마진값
        var margin = 10

        // 선택한 이미지 정 가운데 좌표
        var width = (x + (view.width / 2))
        var height = (y + (view.height / 2))

        var actionMoveX: Float = if (width < (parent.width / 2)) {
            margin.toFloat()
        } else {
            (parent.width - view.width - margin).toFloat()
        }
        Log.d("TEST", "actionMove x : if($width > ${(parent.width / 2)}) = $actionMoveX")

        var actionMoveY: Float = if (height < (parent.height / 2)) {
            margin.toFloat()
        } else {
            (parent.height - view.height - margin).toFloat()
        }
        Log.d("TEST", "actionMove y : if($height > ${(parent.height / 2)}) = $actionMoveY")

        Log.d("TEST", "actionMove:[$actionMoveX, $actionMoveY], x,y:[$x, $y]")
        view.animate()
            .x(actionMoveX)
            .y(actionMoveY)
            .setDuration(100)
            .start()
    }

    private fun getMotionEnabled(): Boolean {
        return binding.includeMotion.motionLayout.isEnabled
    }

    /**
     * motionlayout 애니메이션이 end 상태일 경우 true 넘겨준다.
     */
    private fun checkIsEndState(): Boolean {
        var endState = false
        // end 상태일 경우 활동을 정지 시킨다.
        binding.includeMotion.motionLayout.getTransition(R.id.motion_transition).apply {
            endState = (endConstraintSetId == binding.includeMotion.motionLayout.currentState)
        }
//        Log.d("TEST", "checkIsEndState() endState:::$endState")
        return endState
    }

    companion object {
        fun newInstance() = TestFragment()
    }
}
