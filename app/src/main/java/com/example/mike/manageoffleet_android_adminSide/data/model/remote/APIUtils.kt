package com.example.mike.manageoffleet_android_adminSide.data.model.remote

object ApiUtils {

    val BASE_URL = "https://havistudio.com/MonitoringOfPhones/"

    val apiService: APIService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)
}