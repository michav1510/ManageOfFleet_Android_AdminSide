package com.example.mike.manageoffleet_android_adminSide

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.mike.manageoffleet_android_adminSide.data.model.LocationRecord
import com.example.mike.manageoffleet_android_adminSide.data.model.RespnsForLastLoction
import com.example.mike.manageoffleet_android_adminSide.data.model.remote.APIService
import com.example.mike.manageoffleet_android_adminSide.data.model.remote.ApiUtils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var lastLocationofEachAgent: Array<LocationRecord>? = null

    private var counterUsefullForRefreshing = 1

    private var mAPIService: APIService? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mAPIService = ApiUtils.apiService
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        takeLastLocationOfEachAgent()

        mMap = googleMap


    }


    fun takeLastLocationOfEachAgent(){
        mAPIService?.takeLastLocationRecord()?.subscribeOn(Schedulers.io())?.observeOn(
            AndroidSchedulers.mainThread())
            ?.subscribe(object : Subscriber<RespnsForLastLoction>() {
                override fun onCompleted() {
                    val respArray = lastLocationofEachAgent
                    if(respArray!= null) {
                        for (entry in respArray) {
                            if(entry.latitude!= null && entry.longitude!=null && entry.agentuser!=null && entry.date!=null) {
                                val pos = LatLng(entry.latitude, entry.longitude)
                                mMap.addMarker(MarkerOptions().position(pos).title(""+entry.agentuser+" "+entry.date))
                            }
                        }
                    }
                    //mMap.addMarker(MarkerOptions().position(LatLng(36.4268379,28.2048222)))
                    if(counterUsefullForRefreshing==1) {
                        val positionCenterofRhodes = LatLng(36.4268379, 28.2048222)
                        val zoomLevel = 9.0f //This goes up to 21
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionCenterofRhodes, zoomLevel))
                        counterUsefullForRefreshing++
                    }
                    object : CountDownTimer(10000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {

                        }
                        override fun onFinish() {
                            //first I must clean the markers
                            mMap.clear()
                            takeLastLocationOfEachAgent()
                        }
                    }.start()
                }

                override fun onError(e: Throwable) {

                }

                override fun onNext(response: RespnsForLastLoction) {
                    lastLocationofEachAgent = response.resultarray
                }
            })
    }

}
