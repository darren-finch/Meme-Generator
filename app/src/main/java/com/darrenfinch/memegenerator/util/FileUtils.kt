package com.darrenfinch.memegenerator.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUtils {
    fun saveImage(context: Context, image: Bitmap, imageName: String): Uri? {
        //TODO - Should be processed in another thread
        val imagesFolder = File(context.cacheDir, "images")
        var uri: Uri? = null
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, imageName)
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            uri = FileProvider.getUriForFile(context, AUTHORITIES, file)
        } catch (e: IOException) {
            Log.d("saveImage", "IOException while trying to write file for sharing: " + e.message)
        }
        return uri
    }
}
