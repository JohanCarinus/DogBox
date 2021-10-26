package com.johancarinus.dogbox.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johancarinus.dogbox.model.UriImageData
import dagger.hilt.android.lifecycle.HiltViewModel
import johancarinus.dogbox.R
import javax.inject.Inject

@HiltViewModel
class FullscreenImageViewModel @Inject constructor() : ViewModel() {

    private val PLACEHOLDER = R.drawable.placeholder;

    private val _image: MutableLiveData<UriImageData> by lazy {
        MutableLiveData()
    }

    fun viewImage(uri: Uri) {
        _image.postValue(UriImageData(uri, PLACEHOLDER))
    }

    fun getImage() : LiveData<UriImageData> {
        return _image
    }
}