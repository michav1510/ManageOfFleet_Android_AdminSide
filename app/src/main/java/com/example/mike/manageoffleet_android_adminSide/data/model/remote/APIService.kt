package com.example.mike.manageoffleet_android_adminSide.data.model.remote

import com.example.mike.manageoffleet_android_adminSide.data.model.RespnsForAuthentication
import com.example.mike.manageoffleet_android_adminSide.data.model.RespnsForLastLoction

import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import rx.Observable

interface APIService {

    @POST("---------------------")
    @Headers("Content-Type: application/json;charset=utf-8", "Accept: application/json;charset=utf-8", "Cache-Control: max-age=640000")
    fun sendCredntlsAndWaitForAuthentication(
//            @Body jsonObject: String
        @Body jsonObject : JSONObject
    ): Observable<RespnsForAuthentication>

    @POST("---------------------")
    @Headers("Content-Type: application/json;charset=utf-8", "Accept: application/json;charset=utf-8", "Cache-Control: max-age=640000")
    fun takeLastLocationRecord(): Observable<RespnsForLastLoction>

}