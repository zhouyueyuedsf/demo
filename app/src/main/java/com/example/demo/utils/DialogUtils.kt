package com.example.demo.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import com.example.demo.MyApplication.Companion.getInstance
import com.example.demo.performance.PerformanceActivity
import com.example.demo.views.AccessibilityGuideActivity

/**
 * @author joy zhou
 * @data 2020/5/9
 */
object DialogUtils {
    fun showMagicNoticeDialog(context: Context) {
        val builder = AlertDialog.Builder(getInstance(), 0)
        builder.setTitle("notice")
        builder.setMessage("Failed to activate accessibility. Please turn it off and then on again. If it is still unavailable, please try to restart the phone.")
        builder.setPositiveButton("ok") { dialog: DialogInterface?, which: Int ->
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            context.startActivity(intent)
            (context as Activity).overridePendingTransition(0, 0)
            Handler().postDelayed({
                val intent2 = Intent(context, PerformanceActivity::class.java)
                context.startActivity(intent2)
            }, 500)
        }
        val noticeDialog = builder.create()
        val window = noticeDialog.window
//        noticeDialog.setOnDismissListener {
//            Handler().postDelayed({
//                (context as Activity).finish()
//            }, 600)
//        }
        setWindowType(window)
        noticeDialog.show()
    }

    private fun setWindowType(window: Window?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //总是出现在应用程序窗口之上。
            window!!.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        } else { //总是出现在应用程序窗口之上。
            window!!.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
        }
    }
}