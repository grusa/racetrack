package earth.sochi.goyoga.utils

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import android.util.Log
import android.widget.Toast

class RunningService :Service() {
        private var listener: LocationListener? = null
        var distanceKilometter = 0.0
        var lastLocation: Location? = null
        var speed = 0.0
    val TAG = "RunningService"
        private var binder: IBinder? = null
    
        override fun onCreate() {
            super.onCreate()
            binder = LocalBinder()
            listener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if (lastLocation == null) {
                        lastLocation = location
                    }
                    distanceKilometter = location.distanceTo(lastLocation).toDouble()
                    speed = location.speed.toDouble()
                    lastLocation = location
                    Log.d(TAG,"$speed $distanceKilometter")
                }
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    10f,
                    listener!!
                )
            } else {
                val toast = Toast.makeText(this, "No permission", Toast.LENGTH_LONG).show()
            }
        }


    override fun onBind(intent: Intent?): IBinder? {

        return binder
    }
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): RunningService = this@RunningService
    }

}
