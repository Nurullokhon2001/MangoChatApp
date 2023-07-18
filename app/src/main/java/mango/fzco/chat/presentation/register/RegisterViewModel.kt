package mango.fzco.chat.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mango.fzco.chat.domain.model.RegisterModel
import mango.fzco.chat.domain.use_case.RegisterUserUseCase
import mango.fzco.chat.utils.ResultWrapper
import mango.fzco.chat.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    val registerUserResult = SingleLiveEvent<ResultWrapper<RegisterModel>>()

    fun registerUser(phoneNumber: String, name: String, userName: String) {
        viewModelScope.launch {
            registerUserResult.value = ResultWrapper.Loading
            registerUserResult.value = registerUserUseCase.invoke(phoneNumber, name, userName)
        }
    }
}