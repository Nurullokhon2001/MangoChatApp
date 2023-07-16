package mango.fzco.chat.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mango.fzco.chat.data.dto.request.RequestAuthCodeModelDto
import mango.fzco.chat.data.dto.response.toDomain
import mango.fzco.chat.domain.Repository
import mango.fzco.chat.domain.model.AuthCodeModel
import mango.fzco.chat.utils.ResultWrapper
import mango.fzco.chat.utils.safeApiCall
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val chatApi: ChatApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {

    override suspend fun sendAuthCode(phoneNumber: String): ResultWrapper<AuthCodeModel> {
        return safeApiCall(dispatcher) {
            chatApi.sendAuthCode(RequestAuthCodeModelDto(phoneNumber)).toDomain()
        }
    }
}