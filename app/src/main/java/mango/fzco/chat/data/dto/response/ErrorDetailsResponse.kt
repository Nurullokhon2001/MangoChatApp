package mango.fzco.chat.data.dto.response

data class ErrorDetailsResponse(
    val loc: List<String>,
    val msg: String,
    val type: String
)