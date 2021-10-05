package com.example.domain.repository

import com.example.domain.model.ui.UserUiModel

interface UserRepository {

    suspend fun getUser(): UserUiModel

    suspend fun updateUser(userUiModel: UserUiModel)
}