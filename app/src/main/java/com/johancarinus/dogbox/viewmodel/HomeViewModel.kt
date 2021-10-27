package com.johancarinus.dogbox.viewmodel

import android.net.Uri
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.johancarinus.dogbox.model.core.ErrorData
import com.johancarinus.dogbox.model.core.ErrorSeverity
import com.johancarinus.dogbox.model.core.Event
import com.johancarinus.dogbox.repository.DogsRepository
import com.johancarinus.dogbox.ui.fragments.HomeFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dogsRepository: DogsRepository
) : ViewModel() {

    private val _showLoadingIndicator: MutableLiveData<Boolean> = MutableLiveData(true)
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    private val _error: MutableLiveData<ErrorData> =
        MutableLiveData(ErrorData(ErrorSeverity.NO_ERROR, "No Error"))
    private val _dogUris: MutableLiveData<List<Uri>> by lazy {
        MutableLiveData<List<Uri>>()
    }
    private val _navDirection: MutableLiveData<Event<NavDirections>> by lazy {
        MutableLiveData<Event<NavDirections>>()
    }

    fun initView() {
        if (_dogUris.value == null) {
            _showLoadingIndicator.postValue(true)
            fetchDogUrls()
        }
    }

    fun fetchDogUrls() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val currentValues = _dogUris.value
            val result = dogsRepository.getDogUrls()
            val newValues = mutableListOf<Uri>()

            currentValues?.let { newValues.addAll(it) }
            newValues.addAll(result)

            _dogUris.postValue(newValues)
            _isLoading.postValue(false)
            _showLoadingIndicator.postValue(false)
        }
    }

    fun getDogUris(): LiveData<List<Uri>> {
        return _dogUris
    }

    fun isLoadingInBackground(): LiveData<Boolean> {
        return _isLoading
    }

    fun showIsLoading(): LiveData<Boolean> {
        return _showLoadingIndicator
    }

    fun getNavDirection(): LiveData<Event<NavDirections>> {
        return _navDirection
    }

    fun openImage(uri: Uri) {
        _navDirection.postValue(Event(HomeFragmentDirections.actionHomeFragmentToFullscreenImageFragment(uri)))
    }
}