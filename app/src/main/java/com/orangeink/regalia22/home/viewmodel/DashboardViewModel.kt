package com.orangeink.regalia22.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orangeink.regalia22.common.SuccessResponse
import com.orangeink.regalia22.home.data.model.HomeResponse
import com.orangeink.regalia22.home.data.remote.DashboardRepository
import com.orangeink.regalia22.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _home = MutableLiveData<HomeResponse>()
    val home: LiveData<HomeResponse> = _home

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun dashboard() {
        viewModelScope.launch {
            when (val response = repository.dashboard()) {
                is Resource.Error -> _error.postValue(response.exception)
                is Resource.Success -> {
                    response.data.let {
                        _home.postValue(it)
                    }
                }
                else -> {}
            }
        }
    }
}