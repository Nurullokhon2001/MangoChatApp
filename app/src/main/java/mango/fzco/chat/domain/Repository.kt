package mango.fzco.chat.domain

import mango.fzco.chat.domain.model.CheckAuthCodeModel
import mango.fzco.chat.domain.model.SendAuthCodeModel
import mango.fzco.chat.utils.ResultWrapper

interface Repository {

    suspend fun sendAuthCode(phoneNumber: String): ResultWrapper<SendAuthCodeModel>
    suspend fun checkAuthCode(phoneNumber: String,code:String): ResultWrapper<CheckAuthCodeModel>
}