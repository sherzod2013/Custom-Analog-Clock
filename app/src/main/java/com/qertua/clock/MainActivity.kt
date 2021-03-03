package com.qertua.clock

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.qertua.clock.view.ClockView
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mInterval = 100 // 1 seconds by default, can be changed later

    private var mHandler: Handler? = null

    var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                updateStatus() //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler!!.postDelayed(this, mInterval.toLong())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mHandler = Handler()
        startRepeatingTask()

    }

    fun updateStatus(){
        val textView = findViewById<TextView>(R.id.textView)
        val clockView = findViewById<ClockView>(R.id.my_clock)
        val currentTime = Calendar.getInstance()
        val currentHourIn24Format: Int =
            currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinutes: Int = currentTime.get(Calendar.MINUTE)
        val currentSeconds:Int = currentTime.get(Calendar.SECOND)
        runOnUiThread {
            textView.text = "$currentHourIn24Format : $currentMinutes : $currentSeconds"
        }
        val timeData = TimeData(currentSeconds,currentMinutes,currentHourIn24Format)
        clockView.time = timeData
    }

    fun startRepeatingTask() {
        mStatusChecker.run()
    }

    fun stopRepeatingTask() {
        mHandler!!.removeCallbacks(mStatusChecker)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRepeatingTask()
    }
}