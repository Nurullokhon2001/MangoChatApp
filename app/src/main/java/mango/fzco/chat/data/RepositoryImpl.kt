package mango.fzco.chat.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mango.fzco.chat.data.dto.request.Avatar
import mango.fzco.chat.data.dto.request.CheckAuthCodeRequestDto
import mango.fzco.chat.data.dto.request.RefreshTokenRequestDto
import mango.fzco.chat.data.dto.request.RegisterRequestDto
import mango.fzco.chat.data.dto.request.SendAuthCodeRequestDto
import mango.fzco.chat.data.dto.request.UpdateProfileRequestDto
import mango.fzco.chat.data.dto.response.toDomain
import mango.fzco.chat.domain.Repository
import mango.fzco.chat.domain.model.ChatModel
import mango.fzco.chat.domain.model.ChatsModel
import mango.fzco.chat.domain.model.CheckAuthCodeModel
import mango.fzco.chat.domain.model.ProfileDataModel
import mango.fzco.chat.domain.model.RegisterModel
import mango.fzco.chat.domain.model.SendAuthCodeModel
import mango.fzco.chat.utils.ResultWrapper
import mango.fzco.chat.utils.safeApiCall
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val chatApi: ChatApi,
    private val sharedPreferencesClass: SharedPreferencesClass,
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
            val response =
                chatApi.checkAuthCode(CheckAuthCodeRequestDto(phoneNumber, code))
            sharedPreferencesClass.saveTokens(response.accessToken, response.refreshToken)
            response.toDomain()
        }
    }

    override suspend fun registerUser(
        phoneNumber: String,
        name: String,
        userName: String
    ): ResultWrapper<RegisterModel> {
        return safeApiCall(dispatcher) {
            val response =
                chatApi.registerUser(RegisterRequestDto(phoneNumber, name, userName))
            sharedPreferencesClass.saveTokens(response.accessToken, response.refreshToken)
            response.toDomain()
        }
    }

    override fun getChats(): List<ChatsModel> {
        return FakeChatsData.generateChats()
    }

    override suspend fun getProfileData(): ResultWrapper<ProfileDataModel> {
        return safeApiCall(
            dispatcher,
            {
                val response =
                    chatApi.refreshToken(RefreshTokenRequestDto(sharedPreferencesClass.getRefreshToken()))
                sharedPreferencesClass.saveTokens(response.accessToken, response.refreshToken)

            }
        ) {
            chatApi.getProfileData(BEARER + sharedPreferencesClass.getAccessToken()).profile_data.toDomain()
        }
    }

    override suspend fun updateProfile(
        username: String,
        name: String,
        city: String,
        dateOfBirthday: String,
        avatar: String,
    ) {
        chatApi.updateProfile(
            BEARER + sharedPreferencesClass.getAccessToken(),
            UpdateProfileRequestDto(username, name, city, dateOfBirthday, Avatar(avatar, avatar))
        )
    }

    override fun isHasToken(): Boolean {
        return !sharedPreferencesClass.getAccessToken().isNullOrBlank()
    }

    override fun getChat(): List<ChatModel> {
        return FakeChatData.generateChats()
    }

    companion object {
        private const val BEARER = "Bearer "
    }
}