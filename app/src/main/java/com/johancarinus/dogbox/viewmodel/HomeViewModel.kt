package com.johancarinus.dogbox.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.johancarinus.dogbox.model.ErrorData
import com.johancarinus.dogbox.model.ErrorSeverity
import com.johancarinus.dogbox.model.Event
import com.johancarinus.dogbox.model.UriImageData
import com.johancarinus.dogbox.repository.DogsRepository
import com.johancarinus.dogbox.ui.fragments.HomeFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import johancarinus.dogbox.R
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
    private val _navDirection: MutableLiveData<Event<NavDirections>> by lazy {
        MutableLiveData<Event<NavDirections>>()
    }

    fun getDogUrls(): LiveData<List<UriImageData>> {
        reload()
        return _dogUrls
    }

    fun getNavDirection(): LiveData<Event<NavDirections>> {
        return _navDirection
    }

    fun openImage(uri: Uri) {
        _navDirection.postValue(Event(HomeFragmentDirections.actionHomeFragmentToFullscreenImageFragment(uri)))
        _navDirection
    }

    private fun reload() {
        _dogUrls.postValue(dogsRepository.getDogUrls().map {
            UriImageData(it, PLACEHOLDER)
        })
    }
}