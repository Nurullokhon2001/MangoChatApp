package mango.fzco.chat.domain.use_case

import mango.fzco.chat.domain.Repository
import mango.fzco.chat.domain.model.CheckAuthCodeModel
import mango.fzco.chat.utils.ResultWrapper
import javax.inject.Inject

class CheckAuthCodeUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        code: String
    ): ResultWrapper<CheckAuthCodeModel> {
        return repository.checkAuthCode(phoneNumber, code)
    }
}