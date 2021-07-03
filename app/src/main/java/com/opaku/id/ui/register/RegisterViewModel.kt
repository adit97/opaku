package com.opaku.id.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.opaku.id.core.data.Resource
import com.opaku.id.core.domain.model.RegisterModel
import com.opaku.id.core.domain.model.UserModel
import com.opaku.id.core.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    val userModel = MutableLiveData<RegisterModel>()

    init {
        userModel.value = RegisterModel()
    }

    fun register(): LiveData<Resource<Boolean>> {
        return appUseCase.register(userModel.value!!).asLiveData()
    }
}