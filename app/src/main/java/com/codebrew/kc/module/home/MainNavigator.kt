package com.codebrew.kc.module.home

import com.codebrew.kc.base.BaseInterface
import com.codebrew.kc.retrofit.model.Pagination

interface MainNavigator:BaseInterface {
    fun onLogout(message:String)
    fun getTotalCount(pagination: Pagination)
}