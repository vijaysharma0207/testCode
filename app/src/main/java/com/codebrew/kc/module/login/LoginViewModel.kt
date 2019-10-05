package com.codebrew.kc.module.login

import com.codebrew.kc.appData.DataManager
import com.codebrew.kc.appData.constants.NetworkConstants
import com.codebrew.kc.appData.constants.PrefenceConstants
import com.codebrew.kc.base.BaseViewModel
import com.codebrew.kc.retrofit.model.LoginModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel(dataManager: DataManager) : BaseViewModel<LoginNavigator>(dataManager) {

    fun loginApi(param: HashMap<String, String>) {
        setIsLoading(true)

        compositeDisposable.add(
            dataManager.loginUser(param)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ this.validateResponse(it) }, { this.handleError(it) })
        )
    }

    private fun validateResponse(it: LoginModel?) {

        setIsLoading(false)
        if (it?.code == NetworkConstants.SUCCESS) {
            dataManager.setkeyValue(PrefenceConstants.ACCESS_TOKEN, it.response.token)
            dataManager.setkeyValue(PrefenceConstants.USER_LOGGED_IN, true)
            dataManager.setkeyValue(PrefenceConstants.ACCOUNT_ID, it.response.user.account_id)
            navigator.onSucess()
        } else {
            navigator.onError(it?.status?:"")
        }
    }

    private fun handleError(e: Throwable) {
        setIsLoading(false)
        navigator.onError(handleErrorMsg(e))
    }
}
