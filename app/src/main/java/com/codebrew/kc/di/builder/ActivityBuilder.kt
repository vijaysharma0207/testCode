package com.codebrew.kc.di.builder


import com.codebrew.kc.module.home.MainActivity
import com.codebrew.kc.module.login.LoginActivity
import com.codebrew.kc.module.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {


    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity


    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity


    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): LoginActivity

}





