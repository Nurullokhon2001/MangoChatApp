package mango.fzco.chat.data.dto.response

import com.google.gson.annotations.SerializedName
import mango.fzco.chat.domain.model.CheckAuthCodeModel

data class CheckAuthCodeResponseDto(
    @SerializedName("refresh_token") val refreshToken: String? = EMPTY_REFRESH_TOKEN,
    @SerializedName("access_token") val accessToken: String? = EMPTY_ACCESS_TOKEN,
    @SerializedName("user_id") val userId: Int? = INCORRECT_USER_ID,
    @SerializedName("is_user_exists") val isUserExists: Boolean,
) {
    companion object {
        private const val INCORRECT_USER_ID = -1
        private const val EMPTY_REFRESH_TOKEN = ""
        private const val EMPTY_ACCESS_TOKEN = ""
    }
}

fun CheckAuthCodeResponseDto.toDomain() = CheckAuthCodeModel(
    userId!!,
    isUserExists,
)