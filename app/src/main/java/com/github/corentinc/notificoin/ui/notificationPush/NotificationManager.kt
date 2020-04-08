package com.github.corentinc.notificoin.ui.notificationPush

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.github.corentinc.notificoin.MainActivity
import com.github.corentinc.notificoin.R
import kotlin.random.Random


@Suppress("SameParameterValue")
class NotificationManager(private val context: Context) {
    companion object {
        const val CHANNEL_ID: String = "NotifiCoin"
    }

    init {
        createNotificationChannel()
    }

    private fun sendNotification(title: String, text: String, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_duck)
            .setColor(Color.YELLOW)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        with(NotificationManagerCompat.from(context)) {
            notify(Random.nextInt(0, 10000000), builder.build())
        }

    }

    fun sendNewAdNotifications(
        adsNumber: Int,
        titleString: String,
        searchTitleString: String,
        url: String
    ) {
        if (adsNumber == 1) {
            sendNotification(
                context.getString(R.string.newAdNotificationTitle),
                context.resources.getQuantityString(
                    R.plurals.newAdNotificationText,
                    adsNumber,
                    searchTitleString,
                    titleString
                ),
                url
            )
        } else {
            sendBigtextNotification(
                context.getString(R.string.newAdNotificationTitle),
                context.resources.getQuantityString(
                    R.plurals.newAdNotificationText,
                    adsNumber,
                    adsNumber,
                    searchTitleString
                ),
                context.getString(R.string.newAdNotificationBigText, titleString),
                url
            )
        }
    }

    fun sendBigtextNotification(
        title: String,
        text: String,
        bigText: String,
        url: String? = null,
        intent: Intent? = null
    ) {
        var intentToSend = Intent()
        intent?.let {
            intentToSend = intent
        } ?: run {
            url?.let {
                intentToSend = Intent(Intent.ACTION_VIEW)
                intentToSend.data = Uri.parse(url)
                intentToSend.apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            } ?: run {
                intentToSend = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intentToSend, 0)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_duck)
            .setColor(Color.YELLOW)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
        with(NotificationManagerCompat.from(context)) {
            notify(Random.nextInt(0, 10000000), builder.build())
        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.resources.getString(R.string.channelName)
            val descriptionText = context.resources.getString(R.string.channelDescription)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun generateBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, drawableId) as Drawable
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}