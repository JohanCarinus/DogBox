package com.johancarinus.dogbox.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

private const val REQUEST_CODE_REQUEST_EXTERNAL_STORAGE = 1;
private val PERMISSIONS_STORAGE = listOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

fun verifyStoragePermissions(activity: Activity) {
    val writePermission =
        ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val readPermission =
        ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)

    if (writePermission != PackageManager.PERMISSION_GRANTED && readPermission != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            activity,
            PERMISSIONS_STORAGE.toTypedArray(),
            REQUEST_CODE_REQUEST_EXTERNAL_STORAGE
        )
    }
}