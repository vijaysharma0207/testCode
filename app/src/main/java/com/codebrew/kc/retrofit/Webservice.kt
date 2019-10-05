package com.codebrew.kc.retrofit

import com.codebrew.kc.appData.constants.NetworkConstants
import com.codebrew.kc.retrofit.model.LoginModel
import com.codebrew.kc.retrofit.model.SuccessModel
import com.codebrew.kc.retrofit.model.UserListModel
import io.reactivex.Observable
import retrofit2.http.*

interface Webservice
{
    //http://api.kcdev.pro/v2/auth

    @FormUrlEncoded
    @POST(NetworkConstants.UserLogin)
    fun loginUser(@FieldMap partMap: HashMap<String,String>): Observable<LoginModel>

    @GET(NetworkConstants.UserListing)
    fun getUserListing(@Path("account_id") account_id: String, @QueryMap param:HashMap<String,String>): Observable<UserListModel>


    @DELETE(NetworkConstants.UserLogin)
    fun logout(@Query("token") token: String): Observable<SuccessModel>
}