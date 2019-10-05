package com.codebrew.kc.retrofit

import com.codebrew.kc.BuildConfig
import com.codebrew.kc.di.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/*@Module
class NetModule(internal var context: Context) {*/

@Module
 class  NetModule {


    @Provides
    @NotNull
    @ApplicationScope
    internal fun getApiInterface(retroFit: Retrofit): Webservice {
        return retroFit.create(Webservice::class.java)
    }


    @Provides
    @ApplicationScope
    internal fun getRetrofit(
            loggingInterceptor: HttpLoggingInterceptor
    ): Retrofit {

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient(loggingInterceptor))
                .build()
    }



    @Provides
    @ApplicationScope
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }


    @Provides
    @ApplicationScope
    fun okHttpClient(
            loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
    }


}