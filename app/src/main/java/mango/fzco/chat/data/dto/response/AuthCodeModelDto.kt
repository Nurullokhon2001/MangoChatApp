package mango.fzco.chat.data.dto.response

import mango.fzco.chat.domain.model.AuthCodeModel

data class AuthCodeModelDto(val is_success: Boolean)

fun AuthCodeModelDto.toDomain(): AuthCodeModel = AuthCodeModel(is_success)