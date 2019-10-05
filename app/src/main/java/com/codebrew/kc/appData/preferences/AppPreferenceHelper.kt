package com.codebrew.kc.appData.preferences


import android.content.Context
import android.content.SharedPreferences
import com.codebrew.kc.appData.constants.PrefenceConstants
import com.codebrew.kc.di.PreferenceInfo
import com.google.gson.Gson
import javax.inject.Inject

class AppPreferenceHelper @Inject constructor(
    context: Context,
    @PreferenceInfo private val prefFileName: String
) : PreferenceHelper {


    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)


    override fun setkeyValue(key: String, value: Any) {

        if (value is Int) {
            mPrefs.edit().putInt(key, value).apply()
        } else if (value is Boolean) {
            mPrefs.edit().putBoolean(key, value).apply()
        } else if (value is String) {
            mPrefs.edit().putString(key, value).apply()
        }
    }

    override fun addGsonValue(key: String, value: String) {
        mPrefs.edit().putString(key, value).apply()
    }

    override fun <T> getGsonValue(key: String, type: Class<T>): T? {
        val gson = mPrefs.getString(key, null)
        return if (gson == null) {
            null
        } else {
            try {

                Gson().fromJson(gson, type)
            } catch (e: Exception) {
                throw IllegalArgumentException(
                    "Object storaged with key "
                            + key + " is instanceof other class"
                )
            }

        }
    }


    override fun getKeyValue(key: String, type: String): Any? {


        when (type) {
            PrefenceConstants.TYPE_STRING -> return mPrefs.getString(key, "")
            PrefenceConstants.TYPE_BOOLEAN -> return mPrefs.getBoolean(key, false)
            PrefenceConstants.TYPE_INT -> return mPrefs.getInt(key, 0)
            else -> return null
        }


    }


    override fun logout() {

        if (onRememberInfo()) {
            mPrefs.edit().putString(PrefenceConstants.ACCESS_TOKEN, "").apply()
            mPrefs.edit().putString(PrefenceConstants.ACCOUNT_ID, "").apply()
            mPrefs.edit().putBoolean(PrefenceConstants.USER_LOGGED_IN, false).apply()
        } else {
            mPrefs.edit().clear().apply()
        }


    }


    override fun getCurrentUserLoggedIn(): Boolean {
        return mPrefs.getBoolean(PrefenceConstants.USER_LOGGED_IN, false)
    }

    override fun onRememberInfo(): Boolean {
        return mPrefs.getBoolean(PrefenceConstants.REMEMBER_INFO, false)
    }


}