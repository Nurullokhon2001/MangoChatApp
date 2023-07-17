package mango.fzco.chat.domain.model

data class CheckAuthCodeModel(
    val userId: Int,
    val isUserExists: Boolean,
)