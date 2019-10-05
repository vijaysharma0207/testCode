package com.codebrew.kc.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codebrew.kc.appData.DataManager
import com.codebrew.kc.module.home.MainViewModel
import com.codebrew.kc.module.login.LoginViewModel

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ViewModelProviderFactory @Inject
constructor(private val dataManager: DataManager) : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> return LoginViewModel(
                dataManager
            ) as T

            modelClass.isAssignableFrom(MainViewModel::class.java) -> return MainViewModel(
                dataManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}


