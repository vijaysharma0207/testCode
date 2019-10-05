package com.codebrew.kc.appData


import android.content.Context
import com.codebrew.kc.retrofit.Webservice
import com.codebrew.kc.appData.preferences.PreferenceHelper
import com.codebrew.kc.retrofit.model.LoginModel
import com.codebrew.kc.retrofit.model.SuccessModel
import com.codebrew.kc.retrofit.model.UserListModel
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppDataManager @Inject
constructor(
        private val mContext: Context,
        private val mApiHelper: Webservice,
        private val mPreferencesHelper: PreferenceHelper
) :
        DataManager {


    override fun logout(token: String): Observable<SuccessModel> {
        return mApiHelper.logout(token)
    }


    override fun getUserListing(
        account_id: String,param:HashMap<String,String>
    ): Observable<UserListModel> {
        return mApiHelper.getUserListing(account_id,param)
    }

    override fun loginUser(partMap: HashMap<String, String>): Observable<LoginModel> {
        return mApiHelper.loginUser(partMap)
    }


    override fun addGsonValue(key: String, value: String) {
        return mPreferencesHelper.addGsonValue(key, value)
    }

    override fun <T> getGsonValue(key: String, type: Class<T>): T? {
        return mPreferencesHelper.getGsonValue(key, type)
    }


    override fun setUserAsLoggedOut() {
        mPreferencesHelper.logout()
    }


    override fun setkeyValue(key: String, value: Any) {
        return mPreferencesHelper.setkeyValue(key, value)
    }

    override fun getKeyValue(key: String, type: String): Any? {
        return mPreferencesHelper.getKeyValue(key, type)
    }

    override fun logout() {
        mPreferencesHelper.logout()
    }

    override fun getCurrentUserLoggedIn(): Boolean {
        return mPreferencesHelper.getCurrentUserLoggedIn()
    }


    override fun onRememberInfo(): Boolean {
       return mPreferencesHelper.onRememberInfo()
    }


}