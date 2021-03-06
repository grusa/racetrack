package earth.sochi.goyoga

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import earth.sochi.goyoga.database.Weight
import java.text.SimpleDateFormat
import java.util.*

private val TAG = "UTIL"
public var weightList:List<Weight> = listOf()
fun Context.hasPermission(permission: String): Boolean {

    // Background permissions didn't exit prior to Q, so it's approved by default.
    if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
        return true
    }

    return ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

/**
 * Requests permission and if the user denied a previous request, but didn't check
 * "Don't ask again", we provide additional rationale.
 *
 * Note: The Snackbar should have an action to request the permission.
 */
fun Fragment.requestPermissionWithRationale(
    permission: String,
    requestCode: Int,
    snackbar: Snackbar
) {
    val provideRationale = shouldShowRequestPermissionRationale(permission)

    if (provideRationale) {
        snackbar.show()
    } else {
        requestPermissions(arrayOf(permission), requestCode)
    }
}
fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val theta = lon1 - lon2
    var dist = (Math.sin(deg2rad(lat1))
            * Math.sin(deg2rad(lat2))
            + (Math.cos(deg2rad(lat1))
            * Math.cos(deg2rad(lat2))
            * Math.cos(deg2rad(theta))))
    dist = Math.acos(dist)
    dist = rad2deg(dist)
    dist = dist * 60 * 1.1515
    return dist
}
private fun deg2rad(deg: Double): Double {return deg * Math.PI / 180.0}

private fun rad2deg(rad: Double) : Double {return rad * 180.0 / Math.PI}

fun toSeconds(timeInterval:Long):String {
    if ((timeInterval % 60)<10) {return "0${(timeInterval % 60).toString()}"}
    else {return  (timeInterval % 60).toString()}
}
fun toHours(timeInterval:Long):String {
    if (timeInterval<60*60) return ""
    if ((timeInterval/(60*60))<10) {return "0${(timeInterval/(60*60)).toString()}"}
    else {return  (timeInterval/(60*60)).toString()}
}
fun toMinutes(timeInterval:Long):String {
    if ((timeInterval/60)<10) {return "0${(timeInterval/60).toString()}"}
    else {return  (timeInterval/60).toString()}
}
fun toSeconds (minutes:Long,seconds:Long):Long {
    return minutes*60 + seconds
}

fun stringFromWeight(weight:Weight) :String {
    return "${weight.weight_kilo.toString()}.${weight.weight_gramm.toString()}"
}
fun toDate(date:Long) : String {
    return "01/01/2021"
}
fun getDate(milliSeconds: Long): String? {
    val dateFormat: String = "dd/MM/YYYY"
    val formatter = SimpleDateFormat(dateFormat)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}


