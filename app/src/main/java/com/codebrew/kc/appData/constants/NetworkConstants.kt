package com.codebrew.kc.appData.constants

import com.codebrew.kc.BuildConfig

interface NetworkConstants {

    companion object {
        const val SUCCESS = 200
        const val AUTHFAILED = 498

        const val AUTH_MSG = "AuthError"

        const val DEFAULT_CONTROLLER = "v2"

        const val UserLogin = "$DEFAULT_CONTROLLER/auth"
        const val UserListing = "$DEFAULT_CONTROLLER/accounts/{account_id}/students"

    }
}