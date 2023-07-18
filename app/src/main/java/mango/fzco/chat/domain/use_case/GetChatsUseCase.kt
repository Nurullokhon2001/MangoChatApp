package mango.fzco.chat.domain.use_case

import mango.fzco.chat.domain.Repository
import mango.fzco.chat.domain.model.ChatsModel
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): List<ChatsModel> {
        return repository.getChats()
    }
}