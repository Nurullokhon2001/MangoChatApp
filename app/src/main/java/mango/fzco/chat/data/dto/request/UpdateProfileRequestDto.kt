package mango.fzco.chat.data.dto.request

data class UpdateProfileRequestDto(
    val username: String,
    val name: String = "",
    val city: String = "",
    val birthday: String = "",
    val avatar: Avatar = Avatar(),
)