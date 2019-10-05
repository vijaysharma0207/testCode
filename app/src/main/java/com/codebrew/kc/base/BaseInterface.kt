package com.codebrew.kc.base

interface BaseInterface {

    fun onErrorOccur(message: String)

    fun onSessionExpire()
}