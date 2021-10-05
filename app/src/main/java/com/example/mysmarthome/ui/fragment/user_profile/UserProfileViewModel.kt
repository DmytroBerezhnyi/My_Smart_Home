package com.example.mysmarthome.ui.fragment.user_profile

import androidx.lifecycle.MutableLiveData
import com.example.data.net.repository.UserDataRepository
import com.example.domain.model.ui.UserUiModel
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseViewModel
import com.example.mysmarthome.base.model.NavigationModel
import com.example.mysmarthome.base.model.SnackbarModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val userDataRepository: UserDataRepository) :
    BaseViewModel() {

    val userUiModel = MutableLiveData<UserUiModel>()

    val errorSet = mutableSetOf<String>()

    init {
        launchAsync({
            userUiModel.postValue(userDataRepository.getUser())
        })
    }

    fun updateUserData(userUiModel: UserUiModel) {
        launchAsync(
            block = {
                userDataRepository.updateUser(userUiModel)
            },
            onSuccess = {
                snackLiveEvent.postValue(SnackbarModel(idResMessage = R.string.user_data_has_been_updated))
                navEvent.postValue(NavigationModel(popBack = true))
            })
    }
}