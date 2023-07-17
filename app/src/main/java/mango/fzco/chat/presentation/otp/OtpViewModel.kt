package mango.fzco.chat.presentation.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mango.fzco.chat.domain.model.CheckAuthCodeModel
import mango.fzco.chat.domain.use_case.CheckAuthCodeUseCase
import mango.fzco.chat.utils.ResultWrapper
import mango.fzco.chat.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val checkAuthCodeUseCase: CheckAuthCodeUseCase
) : ViewModel() {

    val checkAuthCodeResult = SingleLiveEvent<ResultWrapper<CheckAuthCodeModel>>()

    fun checkAuthCode(phoneNumber: String, code: String) {
        viewModelScope.launch {
            checkAuthCodeResult.value = ResultWrapper.Loading
            checkAuthCodeResult.value = checkAuthCodeUseCase.invoke(phoneNumber, code)
        }
    }
}