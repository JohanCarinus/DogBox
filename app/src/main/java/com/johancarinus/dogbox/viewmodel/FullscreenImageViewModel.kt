package com.johancarinus.dogbox.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FullscreenImageViewModel @Inject constructor() : ViewModel() {

    private val _image: MutableLiveData<Uri> by lazy {
        MutableLiveData()
    }

    fun viewImage(uri: Uri) {
        _image.postValue(uri)
    }

    fun getImage(): LiveData<Uri> {
        return _image
    }
}