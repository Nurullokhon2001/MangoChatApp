package mango.fzco.chat.data.dto.response

data class Error422DetailsResponseDto(
    val loc: List<String>,
    val msg: String,
    val type: String
)