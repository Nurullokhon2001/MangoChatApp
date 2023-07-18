package mango.fzco.chat.utils

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mango.fzco.chat.data.dto.response.Error404ResponseDto
import mango.fzco.chat.data.dto.response.Error422ResponseDto
import mango.fzco.chat.data.dto.response.ErrorMessageDto
import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorMessageDto? = null) :
        ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()
    object Loading : ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher, apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Exception) {
            throwable.printStackTrace()
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }

                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorMessageDto? {
    return try {
        val message = if (throwable.code() == 422) {
            throwable.response()?.errorBody()?.string().let {
                Gson().fromJson(it.toString(), Error422ResponseDto::class.java).detail[0].msg
            }
        } else {
            throwable.response()?.errorBody()?.string().let {
                Gson().fromJson(
                    it.toString(), Error404ResponseDto::class.java
                ).detail.message
            }
        }
        ErrorMessageDto(message)
    } catch (exception: Exception) {
        exception.printStackTrace()
        null
    }
}
