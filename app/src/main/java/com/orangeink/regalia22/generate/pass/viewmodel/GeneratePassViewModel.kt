package com.orangeink.regalia22.generate.pass.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orangeink.regalia22.common.SuccessResponse
import com.orangeink.regalia22.generate.pass.data.remote.GeneratePassRepository
import com.orangeink.regalia22.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneratePassViewModel @Inject constructor(
    private val repository: GeneratePassRepository
) : ViewModel() {

    private val _generate = MutableLiveData<SuccessResponse>()
    val generate: LiveData<SuccessResponse> = _generate

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun generatePass(
        name: String,
        roll: String,
        email: String,
        allowed: Int,
        amount: String
    ) {
        viewModelScope.launch {
            when (val response = repository.generatePass(name, roll, email, allowed, amount)) {
                is Resource.Error -> _error.postValue(response.exception)
                is Resource.Success -> {
                    response.data.let {
                        _generate.postValue(it)
                    }
                }
                else -> {}
            }
        }
    }
}