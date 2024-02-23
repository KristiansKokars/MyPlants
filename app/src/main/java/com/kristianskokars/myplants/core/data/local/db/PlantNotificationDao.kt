package com.kristianskokars.myplants.core.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kristianskokars.myplants.core.data.local.db.model.NOTIFICATION_TABLE
import com.kristianskokars.myplants.core.data.local.db.model.PlantNotificationDBModel
import com.kristianskokars.myplants.core.data.model.PLANT_TABLE
import com.kristianskokars.myplants.core.data.model.Plant
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantNotificationDao {
    @Insert
    suspend fun insertNotification(notification: PlantNotificationDBModel)

    @Query(
        """
        SELECT * FROM $NOTIFICATION_TABLE 
        JOIN $PLANT_TABLE ON $NOTIFICATION_TABLE.plantId = $PLANT_TABLE.id
        ORDER BY dateInMillis DESC
        """
    )
    fun getNotificationsWithPlant(): Flow<Map<PlantNotificationDBModel, Plant>>

    @Query("SELECT EXISTS(SELECT * FROM $NOTIFICATION_TABLE WHERE isSeen = 0)")
    fun hasUnseenNotifications(): Flow<Boolean>

    @Query("UPDATE $NOTIFICATION_TABLE SET isSeen = 1 WHERE id = :id")
    suspend fun markNotificationAsSeen(id: Int)
}
