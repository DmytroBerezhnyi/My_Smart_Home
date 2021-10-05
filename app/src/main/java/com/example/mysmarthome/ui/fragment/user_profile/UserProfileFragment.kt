package com.example.mysmarthome.ui.fragment.user_profile

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.domain.model.DATE_PATTERN
import com.example.domain.model.ui.UserAddressUiModel
import com.example.domain.model.ui.UserUiModel
import com.example.mysmarthome.R
import com.example.mysmarthome.base.architecture.BaseFragment
import com.example.mysmarthome.base.extension.showDatePicker
import com.example.mysmarthome.base.model.NavigationModel
import com.example.mysmarthome.base.model.SnackbarModel
import com.example.mysmarthome.databinding.FragmentUserProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UserProfileFragment : BaseFragment<FragmentUserProfileBinding, UserProfileViewModel>() {

    override val vm: UserProfileViewModel by viewModels()

    override val layoutId = R.layout.fragment_user_profile

    override fun FragmentUserProfileBinding.initView() {
        tilUserBirthday.editText?.setOnClickListener {
            childFragmentManager.showDatePicker(
                onPositiveClickListener = {
                    vm.userUiModel.value = vm.userUiModel.value?.copy(
                        birthDate = it
                    )
                },
                onCancelListener = {}
            )
        }

        tilFirstName.editText?.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                tilFirstName.isErrorEnabled = true
                tilFirstName.error = getString(R.string.error_empty_first_name)
                vm.errorSet.add(getString(R.string.error_empty_first_name))
            } else {
                tilFirstName.isErrorEnabled = false
                tilFirstName.error = ""
                vm.errorSet.remove(getString(R.string.error_empty_first_name))
            }
        }

        tilLastName.editText?.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                tilFirstName.isErrorEnabled = true
                tilLastName.error = getString(R.string.error_empty_last_name)
                vm.errorSet.add(getString(R.string.error_empty_last_name))
            } else {
                tilFirstName.isErrorEnabled = false
                tilLastName.error = ""
                vm.errorSet.remove(getString(R.string.error_empty_last_name))
            }
        }

        tilUserCity.editText?.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                tilFirstName.isErrorEnabled = true
                tilUserCity.error = getString(R.string.error_empty_city)
                vm.errorSet.add(getString(R.string.error_empty_city))
            } else {
                tilFirstName.isErrorEnabled = false
                tilUserCity.error = ""
                vm.errorSet.remove(getString(R.string.error_empty_city))
            }
        }

        tilUserStreet.editText?.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                tilFirstName.isErrorEnabled = true
                tilUserStreet.error = getString(R.string.error_empty_street)
                vm.errorSet.add(getString(R.string.error_empty_street))
            } else {
                tilFirstName.isErrorEnabled = false
                tilUserStreet.error = ""
                vm.errorSet.remove(getString(R.string.error_empty_street))
            }
        }

        tilUserStreetCode.editText?.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                tilFirstName.isErrorEnabled = true
                tilUserStreetCode.error = getString(R.string.error_empty_street_code)
                vm.errorSet.add(getString(R.string.error_empty_street_code))
            } else {
                tilFirstName.isErrorEnabled = false
                tilUserStreetCode.error = ""
                vm.errorSet.remove(getString(R.string.error_empty_street_code))
            }
        }

        tilUserPostalCode.editText?.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                tilFirstName.isErrorEnabled = true
                tilUserPostalCode.error = getString(R.string.error_empty_postal_code)
                vm.errorSet.add(getString(R.string.error_empty_postal_code))
            } else {
                tilFirstName.isErrorEnabled = false
                tilUserPostalCode.error = ""
                vm.errorSet.remove(getString(R.string.error_empty_postal_code))
            }
        }

        tilUserCountry.editText?.doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                tilFirstName.isErrorEnabled = true
                tilUserCountry.error = getString(R.string.error_empty_country)
                vm.errorSet.add(getString(R.string.error_empty_country))
            } else {
                tilFirstName.isErrorEnabled = false
                tilUserCountry.error = ""
                vm.errorSet.remove(getString(R.string.error_empty_country))
            }
        }

        btnUpdate.setOnClickListener {
            if (vm.errorSet.isEmpty()) {
                vm.updateUserData(
                    UserUiModel(
                        firstName = tilFirstName.editText?.text.toString(),
                        lastName = tilLastName.editText?.text.toString(),
                        userAddress = UserAddressUiModel(
                            city = tilUserCity.editText?.text.toString(),
                            tilUserPostalCode.editText?.text.toString(),
                            tilUserStreet.editText?.text.toString(),
                            tilUserStreetCode.editText?.text.toString(),
                            tilUserCountry.editText?.text.toString()
                        ),
                        birthDate = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(
                            tilUserBirthday.editText?.text.toString()
                        )?.time ?: System.currentTimeMillis()
                    )
                )
            } else {
                showSnackBar(SnackbarModel(vm.errorSet.first()))
            }
        }

        ivBack.setOnClickListener {
            onNavigateTo(NavigationModel(popBack = true))
        }
    }

    override fun UserProfileViewModel.setupViewObservers() {
        userUiModel.observe(viewLifecycleOwner) {
            with(viewDataBinding) {
                tilFirstName.editText?.setText(it.firstName)
                tilLastName.editText?.setText(it.lastName)
                tilUserCity.editText?.setText(it.userAddress.city)
                tilUserCountry.editText?.setText(it.userAddress.country)
                tilUserPostalCode.editText?.setText(it.userAddress.postalCode)
                tilUserStreet.editText?.setText(it.userAddress.street)
                tilUserStreetCode.editText?.setText(it.userAddress.streetCode)

                val formattedDate =
                    SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(Date(it.birthDate))

                tilUserBirthday.editText?.setText(formattedDate)
            }
        }
    }
}