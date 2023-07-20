package mango.fzco.chat.presentation.update_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mango.fzco.chat.domain.model.ProfileDataModel
import mango.fzco.chat.domain.use_case.GetProfileDataUseCase
import mango.fzco.chat.domain.use_case.UpdateProfileUseCase
import mango.fzco.chat.utils.ResultWrapper
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val getProfileDataUseCase: GetProfileDataUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _profileData = MutableLiveData<ResultWrapper<ProfileDataModel>>()
    val profileData: LiveData<ResultWrapper<ProfileDataModel>> get() = _profileData

    init {
        getProfileData()
    }

    fun getProfileData() {
        viewModelScope.launch {
            _profileData.value = ResultWrapper.Loading
            _profileData.value = getProfileDataUseCase.invoke()
        }
    }

    fun updateProfile(
        username: String,
        name: String,
        city: String,
        dateOfBirthday: String,
        avatar: String,
    ) {
        viewModelScope.launch {
            _profileData.value = ResultWrapper.Loading
            updateProfileUseCase.invoke(username, name, city, dateOfBirthday, avatar)
            getProfileData()
        }
    }
}