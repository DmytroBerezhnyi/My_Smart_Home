package com.example.mysmarthome.base.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.RectF
import androidx.exifinterface.media.ExifInterface
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.*
import java.util.*

fun String.getDecryptedBitmap(context: Context): Bitmap? {
    val mainKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    val file = File(this)
    if (file.exists()) {
        val encryptedFile = EncryptedFile.Builder(
            context,
            file,
            mainKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        encryptedFile.openFileInput().use {
            return BitmapFactory.decodeStream(it)
        }
    }
    return null
}

fun String.saveAsEncryptedFile(context: Context): String {
    val bitmap: Bitmap = BitmapFactory.decodeFile(this)
    val mainKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    val file = File(context.filesDir, UUID.randomUUID().toString() + ".jpg")
    val encryptedFile = EncryptedFile.Builder(
        context,
        file,
        mainKey,
        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
    ).build()
    encryptedFile.openFileOutput().use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        it.flush()
    }
    return file.absolutePath
}

fun String.deleteFile() {
    val file = File(this)
    if (file.exists()) file.delete()
}

fun String.getImageOrientation(): Float {
    var rotate = 0f
    try {
        val exif = ExifInterface(this)
        when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)) {
            ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270f
            ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180f
            ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90f
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return rotate
}

fun Bitmap.rotate(degree: Float): Bitmap {
    val matrix = Matrix().apply {
        this.postRotate(degree)
    }
    val scaledBitmap = Bitmap.createScaledBitmap(this, width, height, true)
    return Bitmap.createBitmap(
        scaledBitmap,
        0,
        0,
        scaledBitmap.width,
        scaledBitmap.height,
        matrix,
        true
    )
}

fun Bitmap.scale(width: Int, height: Int): Bitmap {
    val m = Matrix()
    m.setRectToRect(
        RectF(0f, 0f, this.width.toFloat(), this.height.toFloat()), RectF(
            0f, 0f,
            width.toFloat(),
            height.toFloat()
        ), Matrix.ScaleToFit.CENTER
    )
    return Bitmap.createBitmap(this, 0, 0, this.width, this.height, m, true)
}

fun Serializable.toByteArray(): ByteArray {
    ByteArrayOutputStream().use { bos ->
        ObjectOutputStream(bos).use { oos ->
            oos.writeObject(this)
            return bos.toByteArray()
        }
    }
}

inline fun <reified T : Serializable> ByteArray.toObject(): T? {
    ByteArrayInputStream(this).use { byteArrayInputStream ->
        ObjectInputStream(byteArrayInputStream).use { objectInput ->
            with(objectInput.readObject(), {
                if (this is T) {
                    return this
                }
            })
        }
    }
    return null
}