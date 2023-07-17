package mango.fzco.chat.data.dto.response

data class Error422ResponseDto(
    val detail: List<Error422DetailsResponseDto>
):Throwable()