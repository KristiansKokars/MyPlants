package com.kristianskokars.myplants.core.data.local.file

import android.net.Uri

interface FileStorage {
    suspend fun saveFileToInternalAppStorage(uri: Uri, fileName: String): String
    suspend fun deleteFileFromInternalAppStorage(uri: Uri)
}
