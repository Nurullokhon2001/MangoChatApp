package mango.fzco.chat.domain

import mango.fzco.chat.domain.model.ChatModel
import mango.fzco.chat.domain.model.ChatsModel
import mango.fzco.chat.domain.model.CheckAuthCodeModel
import mango.fzco.chat.domain.model.ProfileDataModel
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

    fun getChats(): List<ChatsModel>

    suspend fun getProfileData(): ResultWrapper<ProfileDataModel>
    suspend fun updateProfile(
        username: String,
        name: String,
        city: String,
        dateOfBirthday: String,
        avatar: String,
    )

    fun isHasToken(): Boolean

    fun getChat(): List<ChatModel>
}