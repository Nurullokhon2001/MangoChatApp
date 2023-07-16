package mango.fzco.chat.data.dto.response

data class ErrorResponseDto(
    val detail: List<ErrorDetailsResponse>
):Throwable()