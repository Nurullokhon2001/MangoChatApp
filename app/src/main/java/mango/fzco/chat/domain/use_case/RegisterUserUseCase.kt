package mango.fzco.chat.domain.use_case

import mango.fzco.chat.domain.Repository
import mango.fzco.chat.domain.model.RegisterModel
import mango.fzco.chat.utils.ResultWrapper
import javax.inject.Inject

data class RegisterUserUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        name: String,
        userName: String
    ): ResultWrapper<RegisterModel> {
        return repository.registerUser(phoneNumber, name, userName)
    }
}