package com.codebrew.kc.appData.preferences

interface PreferenceHelper {

    fun setkeyValue(key: String, value: Any)

    fun getKeyValue(key: String, type: String): Any?

    fun addGsonValue(key: String,value: String)

    fun <T>getGsonValue(key: String, type: Class<T>): T?

    fun logout()

    fun getCurrentUserLoggedIn(): Boolean

    fun onRememberInfo():Boolean

}