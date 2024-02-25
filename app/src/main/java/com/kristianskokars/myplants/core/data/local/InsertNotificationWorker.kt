package com.kristianskokars.myplants.core.data.local

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kristianskokars.myplants.R
import com.kristianskokars.myplants.core.data.local.db.PlantNotificationDao
import com.kristianskokars.myplants.core.data.local.db.model.PlantNotificationDBModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@HiltWorker
class InsertNotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted parameters: WorkerParameters,
    private val plantNotificationDao: PlantNotificationDao,
    private val clock: Clock
) : CoroutineWorker(context, parameters) {
    override suspend fun doWork(): Result {
        val plantId = inputData.getString(PLANT_ID) ?: return Result.failure()
        val plantName = inputData.getString(PLANT_NAME) ?: return Result.failure()


        CoroutineScope(Dispatchers.IO + NonCancellable).launch {
            plantNotificationDao.insertNotification(
                PlantNotificationDBModel(
                    plantId = plantId,
                    message = context.getString(R.string.dont_forget_to_water_your_plant, plantName),
                    dateInMillis = clock.now().toEpochMilliseconds()
                )
            )
        }

        return Result.success()
    }

    companion object {
        const val PLANT_ID = "plant_id"
        const val PLANT_NAME = "plant_name"
    }
}
