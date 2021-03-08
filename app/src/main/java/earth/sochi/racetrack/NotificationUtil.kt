package earth.sochi.racetrack

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
//    val eggImage = BitmapFactory.decodeResource(
//        applicationContext.resources,
//        R.drawable.cooked_egg
//    )
//    val bigPicStyle = NotificationCompat.BigPictureStyle()
//        .bigPicture(eggImage)
//        .bigLargeIcon(null)
//    val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java)
//    val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
//        applicationContext,
//        REQUEST_CODE,
//        snoozeIntent,
//        FLAGS)
    val builder = NotificationCompat.Builder(
        applicationContext,
        "applicationContext.getString(R.string.)"
    )
//        .setSmallIcon(R.drawable.cooked_egg)
        .setContentTitle("pplicationContext.getString(R.string.app_name)")
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
//        .setStyle(bigPicStyle)
//        .setLargeIcon(eggImage)
//        .addAction(
//            R.drawable.egg_icon,
//            applicationContext.getString(R.string.snooze),
//            snoozePendingIntent
//        )
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    notify(NOTIFICATION_ID, builder.build())
}
fun NotificationManager.cancelNotifications() {
    cancelAll()
}
//fun setNotification(context:Context){
//    var builder = NotificationCompat.Builder(context, CHANNEL_ID)
//        .setSmallIcon(R.drawable.arrow_up_float)
//        .setContentTitle("textTitle")
//        .setContentText("textContent")
//        .setStyle(NotificationCompat.BigTextStyle()
//            .bigText("Much longer text that cannot fit one line..."))
//        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//}
//private fun createNotificationChannel() {
//    // Create the NotificationChannel, but only on API 26+ because
//    // the NotificationChannel class is new and not in the support library
//        val name = "getString(R.string.channel_name)"
//        val descriptionText = "getString(R.string.channel_description)"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//            description = descriptionText
//        }
//        // Register the channel with the system
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//}
