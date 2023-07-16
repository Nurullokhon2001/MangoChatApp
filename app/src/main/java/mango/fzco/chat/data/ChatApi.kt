package mango.fzco.chat.data

import mango.fzco.chat.data.dto.request.RequestAuthCodeModelDto
import mango.fzco.chat.data.dto.response.AuthCodeModelDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApi {

    @POST("/api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body phoneNumber: RequestAuthCodeModelDto): AuthCodeModelDto
}