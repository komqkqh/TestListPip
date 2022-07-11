package com.example.testlistpip

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.testlistpip.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        ViewCompat.setTransitionName(binding.ivIcon, "iv_icon")

        val testFragment = TestFragment()
        moveView()

        supportFragmentManager.commit {
            replace(
                R.id.id_fragment_layout,
                testFragment
            )
        }
    }

    var moveX = 0f
    var moveY = 0f

    private fun moveView() {
        binding.ivIcon.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                var parentView: View = v.parent as View

                Log.d(
                    "TEST",
                    "touch [${parentView.width}, ${parentView.height}] (x, y):[${v.x}, ${v.y}]"
                )
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
                        v.animate()
                            .x(event.rawX + moveX)
                            .y(event.rawY + moveY)
                            .setDuration(0)
                            .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        actionUpMove(parentView, v, v.x, v.y)
                    }
                }

                return true
            }

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
        })

        binding.ivIcon.setOnClickListener { view ->

            Log.d("TEST", "onClick!!!!")
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("TEST", "Activity onTouchEvent()")
        return super.onTouchEvent(event)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }
}
