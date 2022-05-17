package com.orangeink.regalia22.verification.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orangeink.regalia22.common.SuccessResponse
import com.orangeink.regalia22.util.Resource
import com.orangeink.regalia22.verification.data.model.Pass
import com.orangeink.regalia22.verification.data.remote.VerificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val repository: VerificationRepository
) : ViewModel() {

    private val _pass = MutableLiveData<Pass>()
    val pass: LiveData<Pass> = _pass

    private val _verify = MutableLiveData<SuccessResponse>()
    val verify: LiveData<SuccessResponse> = _verify

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun scan(id: String) {
        viewModelScope.launch {
            when (val response = repository.scan(id)) {
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

    fun verify(id: String, count: Int) {
        viewModelScope.launch {
            when (val response = repository.verify(id, count)) {
                is Resource.Error -> _error.postValue(response.exception)
                is Resource.Success -> {
                    response.data.let {
                        _verify.postValue(it)
                    }
                }
                else -> {}
            }
        }
    }
}