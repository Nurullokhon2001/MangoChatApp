package mango.fzco.chat.domain.use_case

import mango.fzco.chat.domain.Repository
import mango.fzco.chat.domain.model.AuthCodeModel
import mango.fzco.chat.utils.ResultWrapper
import javax.inject.Inject

class SendAuthCodeUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(phoneNumber: String): ResultWrapper<AuthCodeModel> {
        return repository.sendAuthCode(phoneNumber)
    }
}