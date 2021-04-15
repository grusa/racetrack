package earth.sochi.racetrack.workout

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import earth.sochi.racetrack.R
import earth.sochi.racetrack.RacetrackApplication
import earth.sochi.racetrack.databinding.FragmentRunBinding
import earth.sochi.racetrack.toMinutes
import earth.sochi.racetrack.toSeconds
import earth.sochi.racetrack.utils.RunningService
import earth.sochi.racetrack.viewmodels.RunningViewModel
import earth.sochi.racetrack.viewmodels.TimeManagerViewModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RunFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RunFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var speedometer: RunningService? = null
    private var bound = false

    private var trip = false
    private var timing: Long = 0
    private  var timingPause:kotlin.Long = 0
    private  var secondsPause:kotlin.Long = 0
    private  var seconds:kotlin.Long = 0
    private var distanceStr: String? = null
    private  var speedStr:kotlin.String? = null
    private  var timingStr:kotlin.String? = null
    private var speed = 0.0
    private  var distance:kotlin.Double = 0.0
    private var lastLocation : Location? = null
    private lateinit var binding :FragmentRunBinding

    private val runningViewModel: RunningViewModel by activityViewModels() {
        RunningViewModel.RunningViewModelFactory(
            (this.activity?.application as RacetrackApplication).workoutTypeRepository,
            requireActivity().application)
    }

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val speedometerBinder: RunningService.SpeedometerBinder = service as RunningService.SpeedometerBinder
            speedometer = RunningService()//speedometerBinder.speedometer
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            bound = false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        watchMileage()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_run, container, false)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(requireContext(), RunningService::class.java)
        requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRunBinding.bind(view)

        binding.buttonRun.setOnClickListener{
            onClickStart(it)
        }
        binding.buttonStop.setOnClickListener{
            onClickStart(it)
        }
        runningViewModel.elapsedTime.observe(viewLifecycleOwner,{
            it -> binding.tvTime.setText(toSeconds(it))
        })

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RunFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RunFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun onClickStart(view: View) {
        trip = !trip
        timingPause = 0
        secondsPause = 0
        onClickedTrip(trip)
        runningViewModel.elapsedTime.observe(viewLifecycleOwner,{
                it -> binding.tvTime.setText("${toMinutes(it)}:${toSeconds(it)}")
        })
        runningViewModel.getLocationData().observe(viewLifecycleOwner,{
            Toast.makeText(activity,"Location ${it.location.toString()}",Toast.LENGTH_LONG).show()
            if (lastLocation!=null) distance = it.location.distanceTo(lastLocation) + distance
            binding.tvTrip.setText((Math.round(distance*100)/100).toString())
            lastLocation = it.location
        })

    }
    fun onClickedTrip(trip: Boolean) {
        if (trip) {
            timing = Calendar.getInstance().timeInMillis / 1000
            binding.tableLayout.visibility = TableLayout.VISIBLE
            runningViewModel.startTimer(100)
        } else {
            //binding.tableLayout.visibility = TableLayout.INVISIBLE
            runningViewModel.stopTimer()
            timing = 0
        }
    }
    private fun watchMileage() {
        val speedView = binding.message //findViewById(R.id.message) as TextView
        val distanceView = binding.tvTrip //findViewById(R.id.tv_trip) as TextView
        val timeView = binding.tvTime //findViewById(R.id.tv_time) as TextView
        val avView = binding.tvAv // findViewById(R.id.tv_av) as TextView
        val calloriesView = binding.tvCalories//findViewById(R.id.tv_calories) as TextView
        val handler = Handler(Looper.getMainLooper())
        //TODO Calories!
        handler.post(object : Runnable {
            override fun run() {
                if (speedometer != null ) {
                    speed = speedometer!!.speed * 60 * 60 / 1000
                    distance = speedometer?.distanceKilometter!! / 1000
                } // in KM
                distanceStr = String.format("%1$.2f", distance)
                speedStr = String.format("%1$.2f", speed)
                speedView.text = speedStr
                //TODO Callories
                calloriesView.text = "" + Math.round(0.5 * 90 * distance)
                //Callories = 0.5 * weigh * distance
                //Callories = 0.5 * weigh * distance
                seconds =
                    Calendar.getInstance().timeInMillis / 1000 - timing - secondsPause //in Sec
                if (trip) {
                    distanceView.text = distanceStr
                    timingStr = String.format("%02d", seconds / (60 * 60)) + ":" + String.format(
                        "%02d",
                        seconds / 60 % 60
                    ) + ":" + String.format("%02d", seconds % 60)
                    timeView.text = timingStr
                    avView.text = String.format("%1$.2f", distance * 3600 / seconds)
                }
                handler.postDelayed(this, 5_000)
            }
        })
    }

}