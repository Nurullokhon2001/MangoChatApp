package mango.fzco.chat.domain.model

data class MessageModel(
    val isMyMessage: Boolean,
    val message: String,
    val timeOfMessage: String,
    val isRead: Boolean
) : ChatModel()