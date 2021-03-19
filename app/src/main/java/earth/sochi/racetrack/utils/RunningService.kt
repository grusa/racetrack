package earth.sochi.racetrack.utils

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast

class RunningService :Service(){
        private var listener: LocationListener? = null
        private val binder: IBinder = RunningService().SpeedometerBinder()
            var distanceKilometter = 0.0
            var lastLocation: Location? = null
            var speed = 0.0
        inner class SpeedometerBinder : Binder() {
            val speedometer: RunningService
                get() = this@RunningService
        }

        override fun onCreate() {
            super.onCreate()
//            listener = object : LocationListener {
//                override fun onLocationChanged(location: Location) {
//                    if (lastLocation == null) {
//                        lastLocation = location
//                    }
//                    distanceKilometter += location.distanceTo(lastLocation).toDouble()
//                    speed = location.speed.toDouble()
//                    lastLocation = location
//                }
//            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
        override fun onBind(intent: Intent): IBinder {
            return binder
        }
}
