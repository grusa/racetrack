package earth.sochi.racetrack.workout

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import earth.sochi.racetrack.R
import earth.sochi.racetrack.databinding.FragmentWorkoutBinding
import earth.sochi.racetrack.hasPermission
import kotlin.time.ExperimentalTime

/**
 * Start/Stop Fragment for workouts
 */
class WorkoutFragment : Fragment() {

    // This property is only valid between onCreateView and
// onDestroyView.
    enum class StartStop {START,STOP}
    var ss=StartStop.START
    val TAG="WorkoutFragment"
    var distance =0.0
    var time = 0.0
    var startTime=0L

    private lateinit var binding : FragmentWorkoutBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var activityListener: Callbacks? = null

    private lateinit var chronometr :Chronometer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentWorkoutBinding>(
            inflater,
            R.layout.fragment_workout, container, false
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        try {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }else {
                throw IllegalStateException("Permission  ACCESS_FINE_LOCATION is required");
            }
        } catch (e: Exception) {
                throw IllegalStateException("Permission  ACCESS_FINE_LOCATION is required");
        }

        binding.startBt.setOnClickListener(){
//            it.findNavController()
//                .navigate(R.id.action_workoutFragment_to_listWorkoutFragment)
            startNavigating(true)
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Callbacks) {
            activityListener = context

            // If fine location permission isn't approved, instructs the parent Activity to replace
            // this fragment with the permission request fragment.
            if (!context.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                activityListener?.requestFineLocationPermission()
            }

        } else {
            throw RuntimeException("$context must implement LocationUpdateFragment.Callbacks")
        }
    }
    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        }
    override fun onDestroyView() {
        super.onDestroyView()
    }
    @ExperimentalTime
    fun getTime(time:Long):String? {
        return ((time-startTime)).toString()
    }
    fun setNull() {
        ss=StartStop.START
        distance =0.0
        time = 0.0
        startTime=0L

        chronometr.base = SystemClock.elapsedRealtime()


    }
    fun startNavigating(receivingLocation: Boolean) {
        if (ss == StartStop.START) {
            binding.startBt.text = StartStop.STOP.toString()
            setNull()
            startTime = System.currentTimeMillis()
            binding.startBt.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.colorAccent)
            )

            ss=StartStop.STOP
            chronometr.start()
        } else {
            binding.startBt.text = StartStop.START.toString()
            binding.startBt.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.colorPrimary)
            )
            ss=StartStop.START

            binding.startBt.text=StartStop.START.toString()
            chronometr.stop()

        }
    }
    interface Callbacks {
        fun requestFineLocationPermission()
        fun requestBackgroundLocationPermission()
    }
}
