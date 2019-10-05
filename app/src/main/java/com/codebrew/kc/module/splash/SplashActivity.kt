package com.codebrew.kc.module.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.codebrew.kc.app_util.extension.launchActivity
import com.codebrew.kc.module.home.MainActivity
import com.codebrew.kc.R
import com.codebrew.kc.module.login.LoginActivity
import com.codebrew.kc.appData.preferences.PreferenceHelper

import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var prefHelper: PreferenceHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        performDependencyInjection()

        scheduleSplashScreen()
    }

    fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    private fun scheduleSplashScreen() {
        val splashScreenDuration = getSplashScreenDuration()
        Handler().postDelayed(
            {
                // After the splash screen duration, route to the right activities
                routeToAppropriatePage()
                finish()
            },
            splashScreenDuration
        )
    }

    private fun getSplashScreenDuration(): Long {


        return when (prefHelper.getCurrentUserLoggedIn()) {
            true -> {
                // If this is the first launch, make it slow (> 3 seconds) and set flag to false
                3000
            }
            false -> {
                // If the user has launched the app, make the splash screen fast (<= 1 seconds)
                1000
            }
        }
    }

    private fun routeToAppropriatePage() {

            if (prefHelper.getCurrentUserLoggedIn()) {
                launchActivity<MainActivity>()
            } else {
                launchActivity<LoginActivity>()
            }

    }
}
