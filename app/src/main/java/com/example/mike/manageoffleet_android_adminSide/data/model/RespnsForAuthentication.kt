package com.example.mike.manageoffleet_android_adminSide.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class RespnsForAuthentication{


    @SerializedName("result")
    @Expose
    var result: Int? = null


    override fun toString(): String {
        return "Post{" +
                "result='" + result + '\''.toString() +
                '}'.toString()
    }

}