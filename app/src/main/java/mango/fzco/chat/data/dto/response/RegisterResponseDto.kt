package mango.fzco.chat.data.dto.response

import com.google.gson.annotations.SerializedName
import mango.fzco.chat.domain.model.RegisterModel

data class RegisterResponseDto(
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("user_id") val userId: Int,
)

fun RegisterResponseDto.toDomain() = RegisterModel(
    userId = userId,
)