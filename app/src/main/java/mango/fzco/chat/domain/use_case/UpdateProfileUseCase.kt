package mango.fzco.chat.domain.use_case

import mango.fzco.chat.domain.Repository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(
        username: String,
        name: String,
        city: String,
        dateOfBirthday: String,
        avatar: String,
    ) {
        repository.updateProfile(username,name, city, dateOfBirthday, avatar)
    }
}