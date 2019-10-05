package com.codebrew.kc.di.module

import android.app.Application
import android.content.Context
import com.codebrew.kc.appData.AppDataManager
import com.codebrew.kc.appData.DataManager
import com.codebrew.kc.di.PreferenceInfo
import com.codebrew.kc.appData.constants.PrefenceConstants
import com.codebrew.kc.appData.preferences.AppPreferenceHelper
import com.codebrew.kc.appData.preferences.PreferenceHelper
import com.codebrew.kc.app_util.DialogsUtil

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @PreferenceInfo
    internal fun provideprefFileName(): String = PrefenceConstants.SharedPrefenceName

    @Provides
    @Singleton
    internal fun providePrefHelper(appPreferenceHelper: AppPreferenceHelper): PreferenceHelper = appPreferenceHelper

    @Provides
    @Singleton
    fun provideDialogsUtil()= DialogsUtil ()

}