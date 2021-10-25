package com.example.dogbox.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogbox.R
import com.example.dogbox.model.ErrorData
import com.example.dogbox.model.ErrorSeverity
import com.example.dogbox.model.UriImageData
import com.example.dogbox.repository.DogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dogsRepository: DogsRepository
) : ViewModel() {

    private val PLACEHOLDER = R.drawable.placeholder

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _error: MutableLiveData<ErrorData> =
        MutableLiveData(ErrorData(ErrorSeverity.NO_ERROR, "No Error"))
    private val _dogUrls: MutableLiveData<List<UriImageData>> by lazy {
        MutableLiveData<List<UriImageData>>()
    }

    fun getDogUrls(): LiveData<List<UriImageData>> {
        reload()
        return _dogUrls
    }

    fun openImage(uri: Uri) {
        Log.i("OPEN_URI", uri.toString())
    }

    private fun reload() {
        _dogUrls.postValue(dogsRepository.getDogUrls().map {
            UriImageData(it, PLACEHOLDER)
        })
    }
}