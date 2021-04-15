package earth.sochi.racetrack.database

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.joda.time.Interval

class LocationLiveData(context: Context) : LiveData<LocationModel>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    //private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private fun setLocationData(location : Location) {
        value = LocationModel(
            location = location,
            longitude = location.longitude,
            latitude = location.latitude
        )
    }
    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation
            .addOnSuccessListener {location :Location? ->
                location?.also {
                    setLocationData(it)
                }
            }
        startLocationUpdates()
    }
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }
    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult ?: return
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }
    companion object {
        val locationRequest = LocationRequest.create().apply {
            interval = 5_000
            fastestInterval = 5_000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                //.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }
}