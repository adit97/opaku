package com.opaku.id.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.UserModel
import com.opaku.id.core.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    val userModel = MutableLiveData<UserModel>()

    init {
        userModel.value = UserModel()
    }

    fun login(): LiveData<Resource<Long>> {
        return appUseCase.login(userModel.value!!).asLiveData()
    }
}