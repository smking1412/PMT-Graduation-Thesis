package com.shingetsu.mitadriver.Utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shingetsu.mitadriver.Models.DriverMiTa
import com.shingetsu.mitadriver.R
import java.lang.StringBuilder

/**
 * Created by Phạm Minh Tân - Shin on 4/30/2021.
 */
object Common {
    var TAG: String = "PMTAN"
    val NOTI_TITLE: String = "title"
    val NOTI_BODY: String = "body"
    val TOKEN_REFERENCE: String = "Token"
    var currentUser: DriverMiTa? = null
    val DRIVER_INFO_REFERENCE: String = "DriverInfo"
    val DRIVER_LOCATION_REFERENCE: String = "DriverLocation"

    fun buildWelcomeMessage(): String {
        return StringBuilder("Xin chào, ")
            .append(currentUser!!.firstName)
            .append(" ")
            .append(currentUser!!.lastName)
            .toString()
    }

    fun showNotificaion(context: Context, id: Int, title: String?, body: String?, intent: Intent?) {
        var pendingIntent : PendingIntent? = null
        if (intent != null){
            pendingIntent = PendingIntent.getActivity(context,id,intent!!,PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val NOTIFICATION_CHANNEL_ID = "mita_driver"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,"MiTa Driver",
            NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = "App for driver MiTa"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0,1000,500,1000)
            notificationChannel.enableVibration(true)

            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
        builder.setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(false)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setSmallIcon(R.drawable.ic_delivery_man)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.drawable.ic_delivery_man))

        if (pendingIntent != null){
            builder.setContentIntent(pendingIntent!!)

        }
        val notification = builder.build()
        notificationManager.notify(id,notification)
    }

}