package com.orangeink.regalia22.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orangeink.regalia22.common.SuccessResponse
import com.orangeink.regalia22.search.data.remote.SearchRepository
import com.orangeink.regalia22.util.Resource
import com.orangeink.regalia22.verification.data.model.Pass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _pass = MutableLiveData<Pass>()
    val pass: LiveData<Pass> = _pass

    private val _resend = MutableLiveData<SuccessResponse>()
    val resend: LiveData<SuccessResponse> = _resend

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun search(roll: String) {
        viewModelScope.launch {
            when (val response = repository.search(roll)) {
                is Resource.Error -> _error.postValue(response.exception)
                is Resource.Success -> {
                    response.data.let {
                        _pass.postValue(it)
                    }
                }
                else -> {}
            }
        }
    }

    fun resendEmail(email: String, uid: String) {
        viewModelScope.launch {
            when (val response = repository.resendEmail(uid, email)) {
                is Resource.Error -> _error.postValue(response.exception)
                is Resource.Success -> {
                    response.data.let {
                        _resend.postValue(it)
                    }
                }
                else -> {}
            }
        }
    }
}