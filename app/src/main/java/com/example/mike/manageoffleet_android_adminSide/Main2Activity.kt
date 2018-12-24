package com.example.mike.manageoffleet_android_adminSide

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import com.example.mike.manageoffleet_android_adminSide.data.model.LocationRecord
import com.example.mike.manageoffleet_android_adminSide.data.model.RespnsForLastLoction
import com.example.mike.manageoffleet_android_adminSide.data.model.remote.APIService
import com.example.mike.manageoffleet_android_adminSide.data.model.remote.ApiUtils
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class Main2Activity : AppCompatActivity() {

    lateinit var  mdebugView: TextView
    lateinit var submitBtn : Button

    private var lastLocationofEachAgent: Array<LocationRecord>? = null

    private var mAPIService: APIService? = null


    var debug_counter : Int = 1;
    fun writeOnDebugger(message : String?){
        if(message!=null) {
            if (debug_counter % 10 == 0) mdebugView.text = ""
            mdebugView.append(">> " + message + "\n")
            debug_counter++
        }else{

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        submitBtn = findViewById(R.id.button) as Button
        mdebugView = findViewById<TextView>(R.id.debug_view)
        mAPIService = ApiUtils.apiService


        writeOnDebugger("onCreate()")
        submitBtn.setOnClickListener {
           takeLastLocationOfEachAgent()
        }

    }


    fun takeLastLocationOfEachAgent(){
        mAPIService?.takeLastLocationRecord()?.subscribeOn(Schedulers.io())?.observeOn(
            AndroidSchedulers.mainThread())
            ?.subscribe(object : Subscriber<RespnsForLastLoction>() {
                override fun onCompleted() {
                    writeOnDebugger("onCompleted()")

                }

                override fun onError(e: Throwable) {
                    writeOnDebugger("onError()")
                    writeOnDebugger(e.message)

                }

                override fun onNext(response: RespnsForLastLoction) {
                    writeOnDebugger("onNext()")
                    writeOnDebugger(""+response.resultarray)
                    object : CountDownTimer(10000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {

                        }
                        override fun onFinish() {

                        }
                    }.start()
                }
            })
    }
}
