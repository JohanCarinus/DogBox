package com.example.dogbox.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogbox.model.ErrorData
import com.example.dogbox.model.ErrorSeverity
import com.example.dogbox.repository.DogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dogsRepository: DogsRepository
) : ViewModel() {

    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val error: MutableLiveData<ErrorData> =
        MutableLiveData(ErrorData(ErrorSeverity.NO_ERROR, "No Error"))

    private val dogUrls: MutableLiveData<List<Uri>> by lazy {
        MutableLiveData<List<Uri>>()
    }

    fun getDogUrls(): LiveData<List<Uri>> {
        reload()
        return dogUrls
    }

    fun reload() {
        dogUrls.value = dogsRepository.getDogUrls()
    }
}