package com.example.data.net.repository

import android.content.Context
import com.example.data.shared_preference.BaseEncryptedStorageSource
import com.example.domain.extensions.readTextFromAsset
import com.example.domain.model.ui.UserAddressUiModel
import com.example.domain.model.ui.UserUiModel
import com.example.domain.repository.UserRepository
import org.json.JSONObject

class UserDataRepository(
    private val context: Context,
    private val encryptedStorageSource: BaseEncryptedStorageSource<UserUiModel>
) : UserRepository {

    override suspend fun getUser(): UserUiModel {
        if (encryptedStorageSource.contains()) {
            return encryptedStorageSource.get()
        } else {
            val jsonString = context.readTextFromAsset("data.json")
            val userUiModel = jsonString.jsonToUserUiModel()
            encryptedStorageSource.put(userUiModel)
            return encryptedStorageSource.get()
        }
    }

    override suspend fun updateUser(userUiModel: UserUiModel) {
        encryptedStorageSource.put(userUiModel)
    }

    private fun String.jsonToUserUiModel(): UserUiModel {
        val jsonUserUiModel = JSONObject(this).getJSONObject("user")
        val jsonUserAddressUiModel = jsonUserUiModel.getJSONObject("address")

        return UserUiModel(
            firstName = jsonUserUiModel.getString("firstName"),
            lastName = jsonUserUiModel.getString("lastName"),
            userAddress = UserAddressUiModel(
                city = jsonUserAddressUiModel.getString("city"),
                postalCode = jsonUserAddressUiModel.getString("postalCode"),
                street = jsonUserAddressUiModel.getString("street"),
                streetCode = jsonUserAddressUiModel.getString("streetCode"),
                country = jsonUserAddressUiModel.getString("country")
            ),
            birthDate = jsonUserUiModel.getLong("birthDate")
        )
    }
}
