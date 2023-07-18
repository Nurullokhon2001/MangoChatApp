package mango.fzco.chat.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesClass @Inject constructor(context: Context) {

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, 0)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveTokens(accessToken: String?, refreshToken: String?) {
        editor.putString(KEY_ACCESS_TOKEN, accessToken)
        editor.putString(KEY_REFRESH_TOKEN, refreshToken)
        editor.apply()
    }

    fun saveRefreshToken(refreshToken: String) {
        editor.putString(KEY_REFRESH_TOKEN, refreshToken)
        editor.apply()
    }


    fun getRefreshToken(): String? {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, "")
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
    }

    companion object {
        private const val PREF_NAME: String = "AUTH_REFRESH_ACCESS_TOKEN"
        private const val KEY_REFRESH_TOKEN: String = "REFRESH_TOKEN"
        private const val KEY_ACCESS_TOKEN: String = "ACCESS_TOKEN"
    }
}