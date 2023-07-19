package mango.fzco.chat.domain.use_case

import mango.fzco.chat.domain.Repository
import mango.fzco.chat.domain.model.ProfileDataModel
import mango.fzco.chat.utils.ResultWrapper
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): ResultWrapper<ProfileDataModel> {
        return repository.getProfileData()
    }
}