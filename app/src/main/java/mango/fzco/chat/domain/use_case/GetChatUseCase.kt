package mango.fzco.chat.domain.use_case

import mango.fzco.chat.domain.Repository
import mango.fzco.chat.domain.model.ChatModel
import javax.inject.Inject

class GetChatUseCase @Inject constructor(
    private val repository: Repository
) {
    fun invoke(): List<ChatModel> {
        return repository.getChat()
    }
}