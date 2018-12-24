package com.example.mike.manageoffleet_android_adminSide

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mike.manageoffleet_android_adminSide.data.model.RespnsForAuthentication
import com.example.mike.manageoffleet_android_adminSide.data.model.remote.APIService
import com.example.mike.manageoffleet_android_adminSide.data.model.remote.ApiUtils
import org.json.JSONObject
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers



class MainActivity : AppCompatActivity() {

    lateinit var usertext: EditText
    lateinit var passtext: EditText
    lateinit var submitBtn : Button
    lateinit var  mdebugView: TextView


    private var mResponseTv: TextView? = null
    private var mAPIService: APIService? = null


    var debug_counter : Int = 1;
    fun writeOnDebugger(message : String?){
        if(message!=null) {
            if (debug_counter % 8 == 0) mdebugView.text = ""
            mdebugView.append(">> " + message + "\n")
            debug_counter++
        }else{

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usertext = findViewById<View>(R.id.user) as EditText
        passtext = findViewById<View>(R.id.pass) as EditText
        submitBtn = findViewById(R.id.button) as Button
        mdebugView = findViewById<TextView>(R.id.debug_view)
        mResponseTv = findViewById<TextView>(R.id.tv_response)

        mAPIService = ApiUtils.apiService

        submitBtn.setOnClickListener {
            val string_username = usertext.text.toString()
            val string_pass = passtext.text.toString()
            sendPost(string_username,string_pass)
        }
    }

    fun sendPost(user: String, pwd: String) {
        writeOnDebugger("sendPost()")
        val jsonObject = JSONObject()
        jsonObject.put("username", user)
        jsonObject.put("pass",pwd)

//        writeOnDebugger(""+jsonObject)
        mAPIService?.sendCredntlsAndWaitForAuthentication(jsonObject)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Subscriber<RespnsForAuthentication>() {
                override fun onCompleted() {
                    writeOnDebugger("onCompleted()")
                }

                override fun onError(e: Throwable) {
                    writeOnDebugger("onError()")
                    writeOnDebugger(e.message)
                }

                override fun onNext(response: RespnsForAuthentication) {
                    writeOnDebugger("onNext()")
                    writeOnDebugger(""+response.result)
                    if(response.result == 1)
                    {
                        //then we have to start the new activity
                        val resIntent = Intent(this@MainActivity,MapsActivity::class.java)
                        startActivity(resIntent)
                    }
                    //writeOnDebugger(response.toString())
                }
        })
    }

    fun showResponse(response: String) {
        writeOnDebugger("showResponse()")
        if (mResponseTv?.getVisibility() === View.GONE) {
            mResponseTv?.setVisibility(View.VISIBLE)
        }
        mResponseTv?.setText(response)
    }
}
