package com.kristianskokars.myplants.core.data.local

import android.Manifest
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
import com.kristianskokars.myplants.MainActivity
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.data.Constants
import com.kristianskokars.myplants.core.data.DeepLinks
import com.kristianskokars.myplants.core.data.local.db.PlantNotificationDao
import com.kristianskokars.myplants.core.data.local.db.model.PlantNotificationDBModel
import com.kristianskokars.myplants.core.data.toDeepLinkType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@AndroidEntryPoint
class PlantWateringBroadcastReceiver : BroadcastReceiver() {
    @Inject lateinit var plantNotificationDao: PlantNotificationDao

    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return
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
        CoroutineScope(Dispatchers.IO + NonCancellable).launch {
            plantNotificationDao.insertNotification(
                PlantNotificationDBModel(
                    plantId = id,
                    message = context.getString(R.string.dont_forget_to_water_your_plant, name),
                    dateInMillis = Clock.System.now().toEpochMilliseconds()
                )
            )
        }
    }
}
