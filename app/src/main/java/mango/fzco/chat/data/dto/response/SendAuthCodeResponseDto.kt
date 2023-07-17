package mango.fzco.chat.data.dto.response

import mango.fzco.chat.domain.model.SendAuthCodeModel

data class SendAuthCodeResponseDto(val is_success: Boolean)

fun SendAuthCodeResponseDto.toDomain(): SendAuthCodeModel = SendAuthCodeModel(is_success)