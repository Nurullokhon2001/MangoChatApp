package mango.fzco.chat.domain.use_case

import mango.fzco.chat.domain.Repository
import javax.inject.Inject

class IsHasTokenUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Boolean {
        return repository.isHasToken()
    }
}