package com.codebrew.kc.di.component

import android.app.Application
import com.codebrew.kc.di.builder.ActivityBuilder
import com.codebrew.kc.di.module.AppModule
import com.codebrew.kc.KCApp
import com.codebrew.kc.di.ApplicationScope
import com.codebrew.kc.retrofit.NetModule
import com.codebrew.kc.retrofit.Webservice
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@ApplicationScope
@Component(modules = [(AndroidInjectionModule::class), (AppModule::class) ,(ActivityBuilder::class),(NetModule::class)])
interface AppComponent {


    fun getApiInterface(): Webservice


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: KCApp)

}