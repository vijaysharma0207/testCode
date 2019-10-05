package com.codebrew.kc.appData


import com.codebrew.kc.retrofit.Webservice
import com.codebrew.kc.appData.preferences.PreferenceHelper


interface DataManager : Webservice, PreferenceHelper {

    fun setUserAsLoggedOut()

}
