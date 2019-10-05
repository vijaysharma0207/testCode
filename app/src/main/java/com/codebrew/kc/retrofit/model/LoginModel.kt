package com.codebrew.kc.retrofit.model

data class LoginModel(
    val code: Int,
    val response: Response,
    val status: String
)

data class Response(
    val account: String,
    val accountAdmin: Int,
    val api_user_id: String,
    val hash: String,
    val login: String,
    val seconds: String,
    val token: String,
    val user: User,
    val user_type: String
)

data class Group(
    val Record_Creation_Date: String,
    val code: String,
    val id: String,
    val title: String
)

data class User(
    val _user_type: String,
    val account_id: String,
    val first_name: String,
    val groups: List<Group>,
    val id: String,
    val last_name: Any,
    val title: Any
)