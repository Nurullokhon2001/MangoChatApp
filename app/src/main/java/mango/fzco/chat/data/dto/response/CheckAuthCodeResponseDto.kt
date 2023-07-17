package mango.fzco.chat.data.dto.response

import com.google.gson.annotations.SerializedName
import mango.fzco.chat.domain.model.CheckAuthCodeModel

data class CheckAuthCodeResponseDto(
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("is_user_exists") val isUserExists: Boolean,
)

fun CheckAuthCodeResponseDto.toDomain() = CheckAuthCodeModel(
    userId,
    isUserExists,
)