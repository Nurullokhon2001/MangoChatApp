package mango.fzco.chat.domain

import mango.fzco.chat.domain.model.AuthCodeModel
import mango.fzco.chat.utils.ResultWrapper

interface Repository {

    suspend fun sendAuthCode(phoneNumber: String): ResultWrapper<AuthCodeModel>
}