package mango.fzco.chat.data

import mango.fzco.chat.data.dto.request.CheckAuthCodeRequestDto
import mango.fzco.chat.data.dto.request.RefreshTokenRequestDto
import mango.fzco.chat.data.dto.request.RegisterRequestDto
import mango.fzco.chat.data.dto.request.SendAuthCodeRequestDto
import mango.fzco.chat.data.dto.request.UpdateProfileRequestDto
import mango.fzco.chat.data.dto.response.CheckAuthCodeResponseDto
import mango.fzco.chat.data.dto.response.RegisterResponseDto
import mango.fzco.chat.data.dto.response.SendAuthCodeResponseDto
import mango.fzco.chat.data.dto.response.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ChatApi {

    @POST("/api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body body: SendAuthCodeRequestDto): SendAuthCodeResponseDto

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body body: CheckAuthCodeRequestDto): CheckAuthCodeResponseDto

    @POST("/api/v1/users/register/")
    suspend fun registerUser(@Body body: RegisterRequestDto): RegisterResponseDto

    @GET("/api/v1/users/me/")
    suspend fun getProfileData(@Header("Authorization") accessToken: String): UserResponseDto

    @POST("/api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body body: RefreshTokenRequestDto): RegisterResponseDto

    @PUT("/api/v1/users/me/")
    suspend fun updateProfile(
        @Header("Authorization") accessToken: String,
        @Body body: UpdateProfileRequestDto
    )
}