package com.example.domain.model.ui

data class UserUiModel(
    val firstName: String,
    val lastName: String,
    val userAddress: UserAddressUiModel,
    val birthDate: Long
)