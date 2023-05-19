package com.test.bootcounter

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {

    var cTimer: CountDownTimer? = null
    var bodyNotification = ""
    var timestamp_of_the_boot_event = 0
    var time_between_2_last_boot_events = 0
    lateinit var settings: SharedPreferences
    var counterBoot: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settings = getSharedPreferences("ACTION_BOOT_DATA", MODE_PRIVATE)
        counterBoot = settings.getInt("boot_counter", 0)

        showNotification()
        startTimer()
    }

    private fun showNotification() {
        val mBuilder = NotificationCompat.Builder(this)
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
        mBuilder.setContentTitle("Boot counter")

        when {
            counterBoot == 0 -> {
                bodyNotification = "No boots detected"
            }
            counterBoot == 1 -> {
                bodyNotification =
                    "The boot was detected with the timestamp = $timestamp_of_the_boot_event"
            }
            counterBoot > 1 -> {
                bodyNotification = "Last boots time delta = $time_between_2_last_boot_events"
            }
        }

        mBuilder.setContentText(bodyNotification)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        mBuilder.setContentIntent(contentIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, mBuilder.build())
    }


    private fun startTimer() {
        cTimer = object : CountDownTimer(900000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                cancelTimer()

                showNotification()

                startTimer()
            }
        }.start()
    }


    private fun cancelTimer() {
        if (cTimer != null) cTimer!!.cancel()
    }
}
