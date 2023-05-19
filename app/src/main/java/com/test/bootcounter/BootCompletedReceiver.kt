package com.test.bootcounter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent

class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        val settings = context!!.getSharedPreferences("ACTION_BOOT_DATA", MODE_PRIVATE)

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {

            val counterBoot = settings.getInt("boot_counter", 0) + 1

            val editor = settings.edit()
            editor.putInt("boot_counter", counterBoot)
            editor.apply()

            val launchActivity = Intent(context, MainActivity::class.java)
            launchActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(launchActivity)
        }
    }
}