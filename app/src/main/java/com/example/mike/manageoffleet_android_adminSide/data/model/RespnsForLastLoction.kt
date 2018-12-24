package com.example.mike.manageoffleet_android_adminSide.data.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RespnsForLastLoction{


//    @SerializedName("resultarray")
//    @Expose
//    var resultarray: JsonArray? = null

    @SerializedName("resultarray")
    @Expose
    var resultarray: Array<LocationRecord>? = null


    override fun toString(): String {
        return ""+resultarray
    }


//    @SerializedName("resultarray")
//    @Expose
//    var resultarray: Array<LocationRecord>? = null
//
//
//    override fun toString(): String {
//        val allresult = resultarray
//        var resultstr: String = ""
//        if(allresult!=null)
//        {
//            for (entry in allresult)
//            {
//                resultstr += entry
//            }
//        }
//        return resultstr
//    }


}