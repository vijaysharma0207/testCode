package com.codebrew.kc.retrofit.model.custom

data class PageStatusModel(
    var page: String, var status: Boolean
) {
    constructor() : this("", false)
}