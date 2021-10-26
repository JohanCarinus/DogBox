package com.johancarinus.dogbox.util

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import johancarinus.dogbox.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

const val FILE_PROVIDER = "com.johancarinus.dogbox.fileprovider"
const val IMAGE_FOLDER = "dogbox"
const val MIME_JPEG = "image/jpeg"
const val REQUEST_CODE_REQUEST_EXTERNAL_STORAGE = 1;
val PERMISSIONS_STORAGE = listOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

fun Activity.verifyStoragePermissions() {
    val writePermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val readPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)

    if (writePermission != PackageManager.PERMISSION_GRANTED && readPermission != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE.toTypedArray(), REQUEST_CODE_REQUEST_EXTERNAL_STORAGE)
    }
}

fun Activity.saveImage(imageView: ImageView, name: String): Uri? {

    this.verifyStoragePermissions()

    val bitmap = imageView.drawable.toBitmap()
    val fileOutputStream : OutputStream?
    val imageUri : Uri?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val resolver = this.contentResolver
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, MIME_JPEG)
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM" + File.separator + IMAGE_FOLDER)
        imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        fileOutputStream = imageUri?.let { resolver.openOutputStream(it) }
    } else {
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + IMAGE_FOLDER
        val file = File(imagesDir)
        if (!file.exists()) {
            file.mkdir();
        }

        val image = File(imagesDir, name + ".jpg")
        imageUri = FileProvider.getUriForFile(this, FILE_PROVIDER, image)
        fileOutputStream = FileOutputStream(image)
    }

    fileOutputStream.use { imageOutStream ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)
    }

    return imageUri
}


fun Activity.shareImage(uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND);
    intent.type = MIME_JPEG
    intent.putExtra(Intent.EXTRA_STREAM, uri)

    startActivity(Intent.createChooser(intent, getString(R.string.share_image)))
}