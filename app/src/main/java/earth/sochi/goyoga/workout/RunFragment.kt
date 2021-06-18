package earth.sochi.goyoga.workout

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import android.app.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import earth.sochi.goyoga.*
import earth.sochi.goyoga.database.LocationModel
import earth.sochi.goyoga.databinding.FragmentRunBinding
import earth.sochi.goyoga.utils.RunningService
import earth.sochi.goyoga.viewmodels.WeightViewModel
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
    private val TAG ="RF"
    private var speedometer: RunningService?=null
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
    private var interval:Long =0
    private var weigth = 0.0
    private lateinit var binding :FragmentRunBinding
    private lateinit var observerLocation:Observer<LocationModel>
    private lateinit var observerTime:Observer<Long>
    val handler = Handler(Looper.getMainLooper())
    lateinit var  runningRunnable :Runnable
    private val weightViewModel: WeightViewModel by activityViewModels()
    {
        WeightViewModel.WeightViewModelFactory(
            (this.activity?.application as RacetrackApplication).workoutTypeRepository)
    }

//    private val runningViewModel: RunningViewModel by activityViewModels() {
//        RunningViewModel.RunningViewModelFactory(
//            (this.activity?.application as RacetrackApplication).workoutTypeRepository,
//            requireActivity().application)
//    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val speedometerBinder = service as RunningService.LocalBinder
            speedometer = speedometerBinder.getService()
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
        lifecycleScope.launchWhenCreated {
            weigth = weightViewModel.lastWeight().weight_kilo.toDouble()+
                    weightViewModel.lastWeight().weight_gramm.toDouble()/1000
        }
//        Intent(requireContext(), RunningService::class.java).also{
//        requireActivity().application.bindService(it, connection, Context.BIND_AUTO_CREATE)
//        }
    }
    override fun onStop() {
        super.onStop()
        if (bound) {
            try {
                requireActivity().application.unbindService(connection)
            } catch (e:Exception){
                Toast.makeText(activity,e.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRunBinding.bind(view)
        binding.backBt.setOnClickListener{
            view.findNavController().navigateUp()
        }

        binding.buttonRun.setOnClickListener{
            onClickStart(it)
        }
        binding.buttonStop.setOnClickListener{
            onClickStop(it)
        }
        binding.buttonPause.setOnClickListener{
            onClickPause(it)
        }

        if (checkLocationPermission(view.context)) {
            Intent(requireContext(), RunningService::class.java).also {
                requireActivity().application.bindService(it, connection, Context.BIND_AUTO_CREATE)
            }
        }
//        observerTime = Observer<Long> { elapsedTime -> binding.tvTime.setText(
//            "${toHours(elapsedTime)}${toMinutes(elapsedTime)}${toSeconds(elapsedTime)}")}
//        observerLocation = Observer<LocationModel> {locationData -> run {
//            //distance KILLOMETRS
//            if (lastLocation!=null) distance = locationData.location.distanceTo(lastLocation)/1000 + distance
//            binding.tvTrip.setText(String.format("%1$.2f", distance))
//            if (timing>1)
//                binding.tvAv.setText(String.format("%1$.2f",  (distance*60*60)/timing))
//            if (interval>1)
//                binding.message.setText(String.format("%1$.2f",
//                    distance*60*60*1000/(Calendar.getInstance().timeInMillis-interval)))
//            lastLocation = locationData.location
//            interval = Calendar.getInstance().timeInMillis
//        }
//        }



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

    private fun checkLocationPermission(context :Context):Boolean {
        if (checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) return true
        else {
            Toast.makeText(context, "No permission", Toast.LENGTH_LONG).show()
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                ,1);
            return true
        }
    }
    fun onClickStart(view: View) {
        watchMileage()
        trip = !trip
        onClickedTrip(trip)
//        runningViewModel.elapsedTime.observe(viewLifecycleOwner,{
//                it -> binding.tvTime.setText("${toHours(it)}:${toMinutes(it)}:${toSeconds(it)}")
//            timing = it.toLong()
//        })
       // runningViewModel.elapsedTime.observe(viewLifecycleOwner,observerTime)
//        runningViewModel.getLocationData().observe(viewLifecycleOwner,
//            {
//            if (lastLocation!=null) distance = it.location.distanceTo(lastLocation) + distance
//            binding.tvTrip.setText(String.format("%1$.2f", distance/1000))
//            if (timing>1)
//            binding.tvAv.setText(String.format("%1$.2f",  distance/timing * 60 * 60 / 1000))
//            if (interval>1)
//            binding.message.setText(String.format("%1$.2f",
//                distance*60*60*1000/(Calendar.getInstance().timeInMillis-interval)))
//            lastLocation = it.location
//            interval = Calendar.getInstance().timeInMillis
//        })
            //   runningViewModel.getLocationData().observe(viewLifecycleOwner,observerLocation)
        binding.buttonRun.visibility = View.INVISIBLE
        binding.buttonStop.visibility = View.VISIBLE
        binding.buttonPause.visibility = View.VISIBLE
    }
    fun onClickStop(view:View) {
        trip = !trip
        binding.buttonRun.visibility = View.VISIBLE
        binding.buttonStop.visibility = View.INVISIBLE
        binding.buttonPause.visibility = View.INVISIBLE
        binding.message.setText("0.0")
        binding.tvTrip.setText(R.string.defaultDistance)
        binding.tvTime.setText(R.string.defaultTime)
        binding.tvAv.setText(R.string.defaultAV)
        binding.tvCalories.setText(R.string.calories)
        interval = 0
        distance = 0.0
        timing = 0
        handler.removeCallbacksAndMessages(null)
        //        runningViewModel.elapsedTime.removeObserver(observerTime)
//        runningViewModel.getLocationData().removeObserver(observerLocation)
    }
    fun onClickPause(view:View) {
        timingPause = System.currentTimeMillis()
        binding.buttonRun.visibility = View.VISIBLE
        binding.buttonStop.visibility = View.VISIBLE
        binding.buttonPause.visibility = View.INVISIBLE
//        runningViewModel.elapsedTime.removeObserver(observerTime)
//        runningViewModel.getLocationData().removeObserver(observerLocation)
    }
    fun onClickedTrip(trip: Boolean) {
        if (trip) {
            timing = Calendar.getInstance().timeInMillis / 1000
            binding.tableLayout.visibility = TableLayout.VISIBLE
//            runningViewModel.startTimer(100)
        } else {
            //binding.tableLayout.visibility = TableLayout.INVISIBLE
//            runningViewModel.stopTimer()
            timing = 0
        }
    }
    private fun watchMileage() {
        val speedView = binding.message //findViewById(R.id.message) as TextView
        val distanceView = binding.tvTrip //findViewById(R.id.tv_trip) as TextView
        val timeView = binding.tvTime //findViewById(R.id.tv_time) as TextView
        val avView = binding.tvAv // findViewById(R.id.tv_av) as TextView
        val calloriesView = binding.tvCalories//findViewById(R.id.tv_calories) as TextView
        val runningRunnable = object :Runnable {
            override fun run() {
                if (speedometer != null) {
                    speed = (speedometer?.speed?.times(60) ?: 0).toDouble() * 60 / 1000
//                    distance = speedometer?.distanceKilometter?.div(1000) ?: 0.0
                    distance += speedometer?.distanceKilometter?.div(1000) ?: 0.0
                } // in KM
                distanceStr = String.format("%1$.3f", distance)
                speedStr = String.format("%1$.2f", speed)
                speedView.text = speedStr
                calloriesView.text = "" + Math.round(0.5 * weigth * distance)
                Log.d(TAG," Weigth:$weigth Distance:$distance")
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
        }
        handler.post ( runningRunnable)
    }

}