package com.codebrew.kc.retrofit.model

data class UserListModel(
    val code: Int,
    val pagination: Pagination,
    val response: List<UserListResponse>,
    val status: String
)

data class UserListResponse(
    val Record_Creation_Date: String,
    var pos: Int,
    val account_id: String,
    val account_id_for_removed: Any,
    val activate_date: Any,
    val active: String,
    val api_users_id: String,
    val assigned: Int,
    val cc_emails: String,
    val center: String,
    val city: String,
    val company: String,
    val date_of_birth: Any,
    val department: String,
    val email: String,
    val employee_id: String,
    val extra: String,
    val first_login: String,
    val first_name: String,
    val first_name_ar: String,
    val gender: Any,
    val group: String,
    val group_id: String,
    val id: String,
    val isNotice: String,
    val isTna: String,
    val is_test: String,
    val lang: String,
    val last_name: String,
    val last_name_ar: String,
    val national_id: String,
    val national_id_end: String,
    val nationality: String,
    val passed: Int,
    val phone_number: String,
    val position: String,
    val progress: Int,
    val report_frequency: Any,
    val request_group_id: Any,
    val send_progress: String,
    val send_progress_last_date: Any,
    val user_is_active: String,
    val username: String,
    val viewed: Int
)

data class Pagination(
    val currentPage: Int,
    val perPage: Int,
    val start: Int,
    val totalPages: Int,
    val totalRecords: Int
)