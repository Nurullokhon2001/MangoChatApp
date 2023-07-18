package mango.fzco.chat.domain.model

data class ChatsModel(
    val chatId: Int,
    val chatName: String,
    val image: String,
    val lastMessage: String,
    val lastMessageTime: String
)