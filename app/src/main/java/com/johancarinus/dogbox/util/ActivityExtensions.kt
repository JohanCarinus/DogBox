package com.johancarinus.dogbox.util

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.onlyOnFirstCreate(savedInstanceState: Bundle?, work: () -> Unit) {
    if (savedInstanceState == null) {
        work()
    }
}

fun AppCompatActivity.replaceFragment(@IdRes containerId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .commitNow()
}