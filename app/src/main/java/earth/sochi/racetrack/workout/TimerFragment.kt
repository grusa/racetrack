package earth.sochi.racetrack.workout

import android.os.Bundle
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.navigation.fragment.findNavController
import earth.sochi.racetrack.R
import earth.sochi.racetrack.databinding.FragmentTimerBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TimerFragment : Fragment() {
private lateinit var binding :FragmentTimerBinding
    var startStop:Boolean=true

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime
    private lateinit var timer: CountDownTimer

    val currentTimeDate = Transformations.map(currentTime) {
            time ->
        DateUtils.formatElapsedTime(time)
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
        binding.timerTv.text="01.00"
        binding.timerTv.isCountDown
        binding.buttonStart.setOnClickListener{startClick()}
    }
    fun startClick() {
        startStop=!startStop
        if (startStop) {
            binding.timerTv.start()
            timer.start()
            binding.buttonStart.text=getString(R.string.stop)
        } else {
            binding.timerTv.stop()
            binding.buttonStart.text=getString(R.string.start)
        }
    }

//    timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
//
//        override fun onTick(millisUntilFinished: Long) {
//            _currentTime.value = millisUntilFinished/ONE_SECOND
//        }
//
//        override fun onFinish() {
//            _currentTime.value = DONE
//        }
//    }
    companion object {

        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 60000L

    }
}


