package com.orangeink.regalia22.generate.free.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orangeink.regalia22.common.SuccessResponse
import com.orangeink.regalia22.generate.free.data.remote.FreePassRepository
import com.orangeink.regalia22.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FreePassViewModel @Inject constructor(
    private val repository: FreePassRepository
) : ViewModel() {

    private val _success = MutableLiveData<SuccessResponse>()
    val success: LiveData<SuccessResponse> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun studentPass(name: String, roll: String, phone: String) {
        viewModelScope.launch {
            when (val response = repository.studentPass(name, roll, phone)) {
                is Resource.Error -> _error.postValue(response.exception)
                is Resource.Success -> {
                    response.data.let {
                        _success.postValue(it)
                    }
                }
                else -> {}
            }
        }
    }

    fun alumniPass(
        name: String,
        year: String,
        email: String,
        phone: String,
        department: String
    ) {
        viewModelScope.launch {
            when (val response = repository.alumniPass(name, year, email, phone, department)) {
                is Resource.Error -> _error.postValue(response.exception)
                is Resource.Success -> {
                    response.data.let {
                        _success.postValue(it)
                    }
                }
                else -> {}
            }
        }
    }
}