package mango.fzco.chat.domain.model

data class CheckAuthCodeModel(
    val refreshToken: String,
    val accessToken: String,
    val userId: Int,
    val isUserExists: Boolean,
)