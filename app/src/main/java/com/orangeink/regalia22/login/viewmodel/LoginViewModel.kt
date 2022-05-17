package com.orangeink.regalia22.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orangeink.regalia22.login.data.model.Team
import com.orangeink.regalia22.login.data.remote.LoginRepository
import com.orangeink.regalia22.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val _user = MutableLiveData<Team>()
    val user: LiveData<Team> = _user

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun login(email: String) {
        viewModelScope.launch {
            when (val response = repository.login(email)) {
                is Resource.Error -> _error.postValue(response.exception)
                is Resource.Success -> {
                    response.data.let {
                        _user.postValue(it)
                    }
                }
                else -> {}
            }
        }
    }

}