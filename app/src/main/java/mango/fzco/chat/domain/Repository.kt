package mango.fzco.chat.domain

import mango.fzco.chat.domain.model.CheckAuthCodeModel
import mango.fzco.chat.domain.model.RegisterModel
import mango.fzco.chat.domain.model.SendAuthCodeModel
import mango.fzco.chat.utils.ResultWrapper

interface Repository {

    suspend fun sendAuthCode(phoneNumber: String): ResultWrapper<SendAuthCodeModel>
    suspend fun checkAuthCode(phoneNumber: String, code: String): ResultWrapper<CheckAuthCodeModel>
    suspend fun registerUser(
        phoneNumber: String,
        name: String,
        userName: String
    ): ResultWrapper<RegisterModel>
}