package mango.fzco.chat.data.dto.response

import mango.fzco.chat.data.dto.response.ProfileData.Companion.EMPTY_STRING
import mango.fzco.chat.domain.model.ProfileDataModel

data class ProfileData(
    val avatar: String?,
    val birthday: String?,
    val city: String?,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
) {
    companion object {
        const val EMPTY_STRING = ""
    }
}

fun ProfileData.toDomain(): ProfileDataModel {
    return (ProfileDataModel(
        avatar ?: EMPTY_STRING,
        birthday ?: EMPTY_STRING,
        city ?: EMPTY_STRING,
        id,
        name,
        phone,
        username
    ))
}