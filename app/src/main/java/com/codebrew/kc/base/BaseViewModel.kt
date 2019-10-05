package com.codebrew.kc.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.codebrew.kc.appData.DataManager
import com.codebrew.kc.appData.constants.NetworkConstants
import io.reactivex.disposables.CompositeDisposable
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>(val dataManager: DataManager) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var mNavigator: WeakReference<N>? = null


    var navigator: N
        get() = mNavigator?.get()!!
        set(navigator) {
            this.mNavigator = WeakReference(navigator)
        }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }



    fun handleErrorMsg(e: Throwable): String {

        return if (e is HttpException) {
            val responseBody = e.response().errorBody()
            getErrorMessage(responseBody)
        } else {
            e.localizedMessage?:""
        }
    }


    private fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            if (jsonObject.getInt("code") == NetworkConstants.AUTHFAILED) {
                NetworkConstants.AUTH_MSG
            } else {
                jsonObject.getString("status")
            }
        } catch (e: Exception) {
            e.message!!
        }
    }


}
