package mango.fzco.chat.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mango.fzco.chat.data.dto.request.CheckAuthCodeRequestDto
import mango.fzco.chat.data.dto.request.SendAuthCodeRequestDto
import mango.fzco.chat.data.dto.response.toDomain
import mango.fzco.chat.domain.Repository
import mango.fzco.chat.domain.model.CheckAuthCodeModel
import mango.fzco.chat.domain.model.SendAuthCodeModel
import mango.fzco.chat.utils.ResultWrapper
import mango.fzco.chat.utils.safeApiCall
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val chatApi: ChatApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {

    override suspend fun sendAuthCode(phoneNumber: String): ResultWrapper<SendAuthCodeModel> {
        return safeApiCall(dispatcher) {
            chatApi.sendAuthCode(SendAuthCodeRequestDto(phoneNumber)).toDomain()
        }
    }

    override suspend fun checkAuthCode(
        phoneNumber: String,
        code: String
    ): ResultWrapper<CheckAuthCodeModel> {
        return safeApiCall(dispatcher) {
            chatApi.checkAuthCode(CheckAuthCodeRequestDto(phoneNumber, code)).toDomain()
        }
    }
}