package com.codebrew.kc.module.home

import androidx.lifecycle.MutableLiveData
import com.codebrew.kc.appData.DataManager
import com.codebrew.kc.appData.constants.AppConstants
import com.codebrew.kc.appData.constants.NetworkConstants
import com.codebrew.kc.appData.constants.PrefenceConstants
import com.codebrew.kc.base.BaseViewModel
import com.codebrew.kc.retrofit.model.LoginModel
import com.codebrew.kc.retrofit.model.SuccessModel
import com.codebrew.kc.retrofit.model.UserListModel
import com.codebrew.kc.retrofit.model.UserListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainViewModel(dataManager: DataManager) : BaseViewModel<MainNavigator>(dataManager) {

    val userListLiveData: MutableLiveData<List<UserListResponse>> by lazy {
        MutableLiveData<List<UserListResponse>>()
    }


    fun getUserList(start: String, limit:String) {
        setIsLoading(true)

        val param=HashMap<String,String>()
        param["_start"] = start
        param["_limit"] = limit
        param["token"] = dataManager.getKeyValue(PrefenceConstants.ACCESS_TOKEN, PrefenceConstants.TYPE_STRING).toString()

        compositeDisposable.add(
            dataManager.getUserListing(
                dataManager.getKeyValue(PrefenceConstants.ACCOUNT_ID, PrefenceConstants.TYPE_STRING).toString(),param)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ this.validateResponse(it) }, { this.handleError(it) })
        )
    }

    fun logOut()
    {
        setIsLoading(true)

        compositeDisposable.add(
            dataManager.logout(
                dataManager.getKeyValue(PrefenceConstants.ACCOUNT_ID, PrefenceConstants.TYPE_STRING).toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ this.logOutResponse(it) }, { this.handleError(it) })
        )
    }

    private fun logOutResponse(it: SuccessModel?) {
        setIsLoading(false)
        if (it!!.code == NetworkConstants.SUCCESS) {
            dataManager.setUserAsLoggedOut()
            navigator.onLogout(it.response)
        } else {
            navigator.onErrorOccur(it.status)
        }
    }


    private fun validateResponse(it: UserListModel?) {
        setIsLoading(false)

        if (it!!.code == NetworkConstants.SUCCESS) {
            navigator.getTotalCount(it.pagination)
            userListLiveData.setValue(it.response)
        } else {
            navigator.onErrorOccur(it.status)
        }
    }


    private fun handleError(e: Throwable) {
        setIsLoading(false)

        handleErrorMsg(e).let {
            when (it) {
                NetworkConstants.AUTH_MSG -> {
                    navigator.onErrorOccur(AppConstants.AUTH_FAILURE)
                    dataManager.setUserAsLoggedOut()
                    navigator.onSessionExpire()
                }
                else -> navigator.onErrorOccur(it)
            }
        }
    }

}
