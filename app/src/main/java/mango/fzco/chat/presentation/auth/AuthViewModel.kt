package mango.fzco.chat.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mango.fzco.chat.domain.model.AuthCodeModel
import mango.fzco.chat.domain.use_case.SendAuthCodeUseCase
import mango.fzco.chat.utils.ResultWrapper
import mango.fzco.chat.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendAuthCodeUseCase: SendAuthCodeUseCase
) : ViewModel() {

    val authCodeResult = SingleLiveEvent<ResultWrapper<AuthCodeModel>>()

    fun sendAuthCode(phoneNumber: String) {
        viewModelScope.launch {
            authCodeResult.value = ResultWrapper.Loading
            authCodeResult.value = sendAuthCodeUseCase.invoke(phoneNumber)
        }
    }
}