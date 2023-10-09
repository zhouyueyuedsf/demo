package com.example.demo.hardware

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_hard_ware_info.bt_acq_battery_mode
import kotlinx.android.synthetic.main.activity_hard_ware_info.tv_battery_mode

class HardWareInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard_ware_info)
        bt_acq_battery_mode.setOnClickListener {
            val powerMode = (getSystemService(Context.POWER_SERVICE) as PowerManager).isPowerSaveMode
            Log.i(TAG, "onCreate: $powerMode ")
            tv_battery_mode.text = powerMode.toString()
        }
        registerReceiver(object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i(TAG, "onReceive: ${intent?.action} ${intent?.extras}")
                val extras = intent?.extras
                extras?.keySet()?.forEach {
                    Log.i(TAG, "onReceive: key = $it value = ${extras.get(it)}")
                }
            }
        }, IntentFilter("box_low_battery_action_click").apply {
//            addAction(Intent.ACTION_BATTERY_CHANGED)
//            addAction("com.vivo.dismiss.lowbattery.warning")
            addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED)
        })
    }

    companion object {
        const val TAG = "HardWareInfoActivity"
    }
}