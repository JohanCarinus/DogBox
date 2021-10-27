package com.johancarinus.dogbox.util

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.johancarinus.dogbox.exceptions.ShareFailedException
import com.johancarinus.dogbox.model.util.ImageFosMetadata
import johancarinus.dogbox.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private const val FILE_PROVIDER = "com.johancarinus.dogbox.fileprovider"
private const val DCIM_FOLDER = "DCIM"
private const val IMAGE_FOLDER = "dogbox"
private const val MIME_JPEG = "image/jpeg"
private const val FILE_EXTENSION_JPEG = ".jpg"
private const val ERROR_STORAGE = "Error while storing image data."

fun shareImage(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND);
    intent.type = MIME_JPEG
    intent.putExtra(Intent.EXTRA_STREAM, uri)

    context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_image)))
}

fun saveImage(activity: Activity, imageView: ImageView, name: String): Uri {

    verifyStoragePermissions(activity)

    val bitmap = imageView.drawable.toBitmap()

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            buildImageFileOutputStreamWithMediaStore(activity, name)
        } else {
            buildImageFileOutputStreamLegacy(activity, name)
        }.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it.fos)
            return it.fileLocation
        }
    } catch (exception: IOException) {
        throw ShareFailedException(ERROR_STORAGE, exception)
    } catch (exception: ShareFailedException) {
        throw exception
    }
}

@RequiresApi(api = Build.VERSION_CODES.Q)
private fun buildImageFileOutputStreamWithMediaStore(
    context: Context,
    name: String
): ImageFosMetadata {
    val resolver = context.contentResolver
    val contentValues = ContentValues()

    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, MIME_JPEG)
    contentValues.put(
        MediaStore.MediaColumns.RELATIVE_PATH,
        DCIM_FOLDER + File.separator + IMAGE_FOLDER
    )

    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        ?: throw ShareFailedException(ERROR_STORAGE)
    val fileOutputStream = resolver.openOutputStream(imageUri)
        ?: throw ShareFailedException(ERROR_STORAGE)
    return ImageFosMetadata(fileOutputStream, imageUri)
}

private fun buildImageFileOutputStreamLegacy(
    context: Context,
    name: String
): ImageFosMetadata {
    val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        .toString() + File.separator + IMAGE_FOLDER
    val file = File(imagesDir)

    if (!file.exists()) {
        file.mkdir();
    }

    val image = File(imagesDir, name + FILE_EXTENSION_JPEG)
    val imageUri = FileProvider.getUriForFile(context, FILE_PROVIDER, image)
    val fileOutputStream = FileOutputStream(image)

    return ImageFosMetadata(fileOutputStream, imageUri)
}