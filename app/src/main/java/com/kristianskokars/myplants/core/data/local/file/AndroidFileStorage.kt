package com.kristianskokars.myplants.core.data.local.file

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class AndroidFileStorage @Inject constructor(
    @ApplicationContext private val context: Context,
) : FileStorage {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun saveFileToInternalAppStorage(uri: Uri, fileName: String): String = withContext(ioDispatcher) {
        val file = File(context.filesDir, "${fileName}-${Random.nextInt(from = 0, until = Int.MAX_VALUE)}")
        file.createNewFile()

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { fos ->
                inputStream.copyTo(fos)
            }
        }

        return@withContext file.toUri().toString()
    }

    override suspend fun deleteFileFromInternalAppStorage(uri: Uri) = withContext(ioDispatcher) {
        // TODO: create the deletion of files
    }
}
