package com.example.mike.manageoffleet_android_adminSide.data.model



import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigInteger


public class LocationRecord {

    @SerializedName("agentuser")
    @Expose
    val agentuser: String? = null

    @SerializedName("timeinmilli")
    @Expose
    val timeinmilli: BigInteger? = null

    @SerializedName("date")
    @Expose
    val date: String? = null

    @SerializedName("latitude")
    @Expose
    val latitude: Double? = null

    @SerializedName("longitude")
    @Expose
    val longitude: Double? = null


}