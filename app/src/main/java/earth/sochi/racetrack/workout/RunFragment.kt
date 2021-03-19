package earth.sochi.racetrack.workout

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import earth.sochi.racetrack.R
import earth.sochi.racetrack.databinding.FragmentRunBinding
import earth.sochi.racetrack.utils.RunningService
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
    private val mTextMessage: TextView? = null
    private val buttonFab: FloatingActionButton? = null
    private  var buttonFabRun:FloatingActionButton? = null
    private  var buttonFabStop:FloatingActionButton? = null
    private  var buttonFabPause:FloatingActionButton? = null

    private var trip = false
    private var pressedFAB = false
    private var timing: Long = 0
    private  var timingPause:kotlin.Long = 0
    private  var secondsPause:kotlin.Long = 0
    private  var seconds:kotlin.Long = 0
    private var distanceStr: String? = null
    private  var speedStr:kotlin.String? = null
    private  var timingStr:kotlin.String? = null
    private var speed = 0.0
    private  var distance:kotlin.Double = 0.0
    var tableLayoutGo: TableLayout? = null
    var tableLayoutWear:TableLayout? = null
    var contaner: ConstraintLayout? = null
    private lateinit var binding :FragmentRunBinding

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
        binding.floatingActionButton.setOnClickListener{
            onClickedFab(it)
        }
        binding.floatingActionButtonRun.setOnClickListener{
            onClickStart(it)
        }

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

    fun onClickedFab(view: View) {
        pressedFAB = if (pressedFAB) {
            binding.floatingActionButton.setImageDrawable(getDrawable(requireContext(),R.drawable.ic_add_black_48dp))
            binding.floatingActionButtonRun.hide()
            binding.floatingActionButtonPause.hide()
            binding.floatingActionButtonStop.hide()
            binding.container.setBackgroundColor(getColor(requireContext(), R.color.white))
            !pressedFAB
        } else {
            binding.floatingActionButton.setRippleColor(getColor(requireContext(),R.color.colorPrimary))
            binding.floatingActionButton.setImageDrawable(getDrawable(requireContext(),R.drawable.ic_clear_white_48dp))
            binding.floatingActionButtonRun.show()
            binding.floatingActionButtonPause.show()
            binding.floatingActionButtonStop.show()
            binding.container.setBackgroundColor(getColor(requireContext(),R.color.colorPrimaryDarkSuper))
            !pressedFAB
        }
    }
    fun onClickStart(view: View) {
        trip = true
        timingPause = 0
        secondsPause = 0
        onClickedTrip(trip)
        onClickedFab(view)
    }
    fun onClickedTrip(trip: Boolean) {
        if (trip) {
            timing = Calendar.getInstance().timeInMillis / 1000
            binding.tableLayout.visibility = TableLayout.VISIBLE
            binding.tableLayoutWear.visibility = TableLayout.VISIBLE
        } else {
            binding.tableLayout.visibility = TableLayout.INVISIBLE
            binding.tableLayoutWear.visibility = TableLayout.INVISIBLE
            timing = 0
        }
    }
    private fun watchMileage() {
        val speedView = binding.message //findViewById(R.id.message) as TextView
        val distanceView = binding.tvTrip //findViewById(R.id.tv_trip) as TextView
        val timeView = binding.tvTime //findViewById(R.id.tv_time) as TextView
        val avView = binding.tvAv // findViewById(R.id.tv_av) as TextView
        val calloriesView = binding.tvCalories//findViewById(R.id.tv_calories) as TextView
        val handler = Handler()
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
                handler.postDelayed(this, 1000)
            }
        })
    }

}