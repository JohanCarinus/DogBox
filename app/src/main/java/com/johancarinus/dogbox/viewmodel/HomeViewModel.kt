package com.johancarinus.dogbox.viewmodel

import android.net.Uri
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import androidx.paging.PagingData
import androidx.paging.map
import com.johancarinus.dogbox.model.*
import com.johancarinus.dogbox.repository.DogsRepository
import com.johancarinus.dogbox.ui.fragments.HomeFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import johancarinus.dogbox.R
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dogsRepository: DogsRepository
) : ViewModel() {

    private val PLACEHOLDER = R.drawable.placeholder

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    private val _error: MutableLiveData<ErrorData> =
        MutableLiveData(ErrorData(ErrorSeverity.NO_ERROR, "No Error"))
    private val _dogUrls: MutableLiveData<List<UriImageData>> by lazy {
        MutableLiveData<List<UriImageData>>()
    }
    private val _navDirection: MutableLiveData<Event<NavDirections>> by lazy {
        MutableLiveData<Event<NavDirections>>()
    }

    fun initView() {
        if (_dogUrls.value == null) {
            _showLoading.postValue(true)
            fetchDogUrls()
        }
    }

    fun fetchDogUrls() {
        viewModelScope.launch {
            _loading.postValue(true)
            val result = dogsRepository.getDogUrls()
            val newValue = mutableListOf<UriImageData>()
            _dogUrls.value?.let { newValue.addAll(it) }
            newValue.addAll(result)
            _dogUrls.postValue(newValue)
            _loading.postValue(false)
            _showLoading.postValue(false)
        }
    }

    fun getDogUrls(): MutableLiveData<List<UriImageData>> {
        return _dogUrls
    }

    fun isLoading(): LiveData<Boolean> {
        return _loading
    }

    fun showIsLoading(): LiveData<Boolean> {
        return _showLoading
    }

    fun getNavDirection(): LiveData<Event<NavDirections>> {
        return _navDirection
    }

    fun openImage(uri: Uri) {
        _navDirection.postValue(Event(HomeFragmentDirections.actionHomeFragmentToFullscreenImageFragment(uri)))
        _navDirection
    }
}