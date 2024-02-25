package com.kristianskokars.myplants.core.data.local

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.kristianskokars.myplants.MainActivity
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.data.Constants
import com.kristianskokars.myplants.core.data.DeepLinks
import com.kristianskokars.myplants.core.data.local.db.PlantNotificationDao
import com.kristianskokars.myplants.core.data.toDeepLinkType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class PlantWateringBroadcastReceiver : BroadcastReceiver() {
    @Inject lateinit var plantNotificationDao: PlantNotificationDao
    @Inject lateinit var alarmManager: AlarmManager
    @Inject lateinit var timeZone: TimeZone
    @Inject lateinit var workManager: WorkManager

    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        val intentId = intent.extras?.getInt(INTENT_ID) ?: return
        val alarmTime = intent.extras?.getLong(ALARM_TIME) ?: return
        val isEveryday = intent.extras?.getBoolean(IS_EVERYDAY) ?: return
        val id = intent.extras?.getString(DeepLinks.Type.ID) ?: return
        val type = intent.extras?.getString(DeepLinks.Type.KEY)?.toDeepLinkType() ?: return
        val name = intent.extras?.getString(DeepLinks.Extra.NAME.toString()) ?: return

        val notificationManager = NotificationManagerCompat.from(context)
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            DeepLinks.item(id, type).toUri(),
            context,
            MainActivity::class.java
        )
        val deepLinkPendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val contentTitle = context.getString(R.string.plant_reminder_notification_title, name)
        val contentText = context.getString(R.string.plant_reminder_notification_text)
        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_app_logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(deepLinkPendingIntent)
            .build()

        notificationManager.notify(id.hashCode(), notification)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            intentId,
            Intent(context, PlantWateringBroadcastReceiver::class.java).apply {
                putExtra(INTENT_ID, intentId)
                putExtra(ALARM_TIME, intentId)
                putExtra(DeepLinks.Type.ID, id)
                putExtra(DeepLinks.Type.KEY, type.toString())
                putExtra(DeepLinks.Extra.NAME.toString(), name)
            },
            PendingIntent.FLAG_IMMUTABLE
        )

        try {
            val initialAlarmTime = Instant
                .fromEpochSeconds(alarmTime)
                .toLocalDateTime(timeZone)
            val nextAlarmTime = initialAlarmTime
                .date
                .plus(if (isEveryday) DatePeriod(days = 1) else DatePeriod(days = 7))
                .atTime(initialAlarmTime.time)
                .toInstant(timeZone)
                .toEpochMilliseconds()

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                nextAlarmTime,
                pendingIntent
            )
        } catch (e: SecurityException) {
            // TODO: handle exception
        }

        val insertNotificationRequest = OneTimeWorkRequestBuilder<InsertNotificationWorker>()
            .setInputData(
                workDataOf(
                    InsertNotificationWorker.PLANT_ID to id,
                    InsertNotificationWorker.PLANT_NAME to name
                )
            )
            .build()
        workManager.enqueue(insertNotificationRequest)
    }

    companion object {
        const val INTENT_ID = "intent_id"
        const val ALARM_TIME = "alarm_time"
        const val IS_EVERYDAY = "is_everyday"
    }
}
