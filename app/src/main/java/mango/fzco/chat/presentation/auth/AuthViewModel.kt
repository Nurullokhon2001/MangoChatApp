package mango.fzco.chat.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mango.fzco.chat.domain.model.SendAuthCodeModel
import mango.fzco.chat.domain.use_case.SendAuthCodeUseCase
import mango.fzco.chat.utils.ResultWrapper
import mango.fzco.chat.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendAuthCodeUseCase: SendAuthCodeUseCase
) : ViewModel() {

    val sendAuthCodeResult = SingleLiveEvent<ResultWrapper<SendAuthCodeModel>>()

    fun sendAuthCode(phoneNumber: String) {
        viewModelScope.launch {
            sendAuthCodeResult.value = ResultWrapper.Loading
            sendAuthCodeResult.value = sendAuthCodeUseCase.invoke(phoneNumber)
        }
    }
}