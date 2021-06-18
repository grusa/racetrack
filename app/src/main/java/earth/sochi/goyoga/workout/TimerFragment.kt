package earth.sochi.goyoga.workout

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import earth.sochi.goyoga.*
import earth.sochi.goyoga.databinding.FragmentTimerBinding
import earth.sochi.goyoga.viewmodels.TimeManagerViewModel
import earth.sochi.goyoga.viewmodels.WorkoutTypeViewModel


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TimerFragment : Fragment() {
private lateinit var binding :FragmentTimerBinding
    private var startStop:Boolean=true
    private val CHANNEL_ID = "RACE_Notify"
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime
    private lateinit var timer: CountDownTimer

    private val timeManagerViewModel: TimeManagerViewModel by activityViewModels() {
    TimeManagerViewModel.TimeManagerViewModelFactory((this.activity?.application as RacetrackApplication).workoutTypeRepository)
    }
    private val workoutTypeViewModel: WorkoutTypeViewModel by viewModels ()
    {
        WorkoutTypeViewModel.WorkoutTypeViewModelFactory(
            (this.activity?.application as RacetrackApplication).workoutTypeRepository)
    }

    val currentTimeDate = Transformations.map(currentTime) {
            time ->
        DateUtils.formatElapsedTime(time)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerBinding.bind(view)
        //binding.secondsTv
        // MINUTES
//        binding.upMinutesBt.setOnClickListener{ binding.minutesTv.setText ((Integer.parseInt(binding.minutesTv.text.toString())+1).toString())}
//        binding.downMinutesBt.setOnClickListener{ binding.minutesTv.setText ((Integer.parseInt(binding.minutesTv.text.toString())-1).toString())}
        binding.upMinutesBt.setOnClickListener(clickListener)
        binding.downMinutesBt.setOnClickListener(clickListener)
        // SECONDS
        binding.upSecondBt.setOnClickListener(clickListener)
        binding.downSecondBt.setOnClickListener(clickListener)
        //binding.upSecondBt.setOnClickListener{ binding.secondsTv.setText ((Integer.parseInt(binding.secondsTv.text.toString())+1).toString())}
        //binding.downSecondBt.setOnClickListener{ binding.secondsTv.setText ((Integer.parseInt(binding.secondsTv.text.toString())-1).toString())}
        binding.buttonResume.visibility = View.INVISIBLE
        binding.buttonStart.setOnClickListener{startClick()}
        timeManagerViewModel.elapsedTime.observe(viewLifecycleOwner, Observer {
            it ->
            run {
                binding.secondsTv.setText(toSeconds(it).toString())
                binding.minutesTv.setText(toMinutes(it).toString())
            }
        })
        timeManagerViewModel.startAlarm.observe(viewLifecycleOwner, Observer {
                it -> if (it) goAlarm()
        })
        binding.buttonResume.setOnClickListener {resumeClick()}
//        binding.buttonResume.visibility = View.INVISIBLE


        binding.buttonStart.setOnClickListener {startClick()}

    }
    fun resumeClick() {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(10)
                //.setTitle("Select Appointment time")
                .build()
        picker.show(parentFragmentManager, "tag");

//        timeManagerViewModel.resetTimer()
//        binding.buttonStart.text = getText(R.string.start)
//        startStop = true
    }
    fun goAlarm() {
        binding.buttonStart.text = getText(R.string.start)
        startStop = true
        Toast.makeText(this.context,"Super ",Toast.LENGTH_LONG).show()
        val notificationId = setNotification(this.requireContext())
    }
    fun startClick() {
        if (startStop) {
            timeManagerViewModel.startTimer(
                toSeconds(binding.minutesTv.text.toString().toLong()
                    ,binding.secondsTv.text.toString().toLong()))
            binding.buttonStart.text = getText(R.string.stop)
            startStop = false
        } else {
            val saveTime = toSeconds(binding.minutesTv.text.toString().toLong()
                    ,binding.secondsTv.text.toString().toLong())
            timeManagerViewModel.resetTimer()
            binding.secondsTv.setText(toSeconds(saveTime).toString())
            binding.minutesTv.setText(toMinutes(saveTime).toString())
            binding.buttonStart.text = getText(R.string.start)
            startStop = true
        }
    }
    val clickListener = View.OnClickListener {view ->
        when (view.getId()) {
            R.id.up_second_bt -> { if ((Integer.parseInt(binding.secondsTv.text.toString())) < 59)
                    binding.secondsTv.setText((Integer.parseInt(binding.secondsTv.text.toString()) + 1).toString())
            }
            R.id.down_second_bt -> { if ((Integer.parseInt(binding.secondsTv.text.toString())) > 0)
                    binding.secondsTv.setText ((Integer.parseInt(binding.secondsTv.text.toString())-1).toString())
            }
            R.id.up_minutes_bt -> { if (Integer.parseInt(binding.minutesTv.text.toString())< 180 )
                binding.minutesTv.setText ((Integer.parseInt(binding.minutesTv.text.toString())+1).toString())
            }
            R.id.down_minutes_bt -> { if (Integer.parseInt(binding.minutesTv.text.toString())>0 )
                binding.minutesTv.setText ((Integer.parseInt(binding.minutesTv.text.toString())-1).toString())
            }
        }
    }
    companion object {
        // Time when the game is over
        private const val DONE = 0L
        // Countdown time interval
        private const val ONE_SECOND = 1000L
        // Total time for the game
        private const val COUNTDOWN_TIME = 60000L
    }
    private fun setNotification(context:Context):Int  {
        val notificationId:Int=0
        val builder =
        NotificationCompat.Builder (this.requireContext(),CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Title")
            .setContentText("Notification text")

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
        return notificationId
    }
}


