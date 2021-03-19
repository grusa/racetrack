package earth.sochi.racetrack.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import earth.sochi.racetrack.R
import earth.sochi.racetrack.RacetrackApplication
import earth.sochi.racetrack.databinding.FragmentBreathBinding
import earth.sochi.racetrack.viewmodels.BreathViewModel
import earth.sochi.racetrack.viewmodels.TimeManagerViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
private lateinit var binding : FragmentBreathBinding
private var startStop:Boolean = true

class BreathFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var inhale: Int = 4
    private var firstPause: Int = 1
    private var exhale: Int = 4
    private var secondPause: Int = 1

    private val breathViewModel: BreathViewModel by activityViewModels() {
        BreathViewModel.BreathViewModelFactory((this.activity?.application as RacetrackApplication).workoutTypeRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breath, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentBreathBinding.bind(view)
        binding.inhaleTv.setText(inhale.toString())
        binding.firstPauseTv.setText(firstPause.toString())
        binding.exhaleTv.setText(exhale.toString())
        binding.secondPauseTv.setText(secondPause.toString())

        binding.buttonStart.setOnClickListener{
            onStartClick()
        }
        breathViewModel.elapsedTime.observe(viewLifecycleOwner, Observer {
            it -> running(it)
        })
        super.onViewCreated(view, savedInstanceState)
    }
    private fun onStartClick() {
        if (startStop) {
            binding.buttonStart.text = getText(R.string.stop)

            getValueBreathe()
            breathViewModel.startTimer(getTimerPeriod())
        } else {
            binding.buttonStart.text = getText(R.string.start)
            binding.inhaleTr.setBackgroundResource(R.color.white)
            binding.exhaleTr.setBackgroundResource(R.color.white)
            binding.firstPauseTr.setBackgroundResource(R.color.white)
            binding.secondPauseTr.setBackgroundResource(R.color.white)
            binding.seekBar.progress=0
            breathViewModel.resetTimer()
        }
        startStop = !startStop
    }
    fun running(deal:Long) {
        when (deal) {
            (inhale+exhale+firstPause+secondPause).toLong() -> binding.inhaleTr.setBackgroundResource(R.color.colorAccent)

            (exhale+firstPause+secondPause).toLong() -> {binding.inhaleTr.setBackgroundResource(R.color.white)
                    binding.firstPauseTr.setBackgroundResource(R.color.colorAccent)
            binding.seekBar.progress=1}

            (exhale+secondPause).toLong() -> {binding.firstPauseTr.setBackgroundResource(R.color.white)
                    binding.exhaleTr.setBackgroundResource(R.color.colorAccent)
                binding.seekBar.progress=2}

            (secondPause).toLong() -> {binding.exhaleTr.setBackgroundResource(R.color.white)
                    binding.secondPauseTr.setBackgroundResource(R.color.colorAccent)
                binding.seekBar.progress=3}

            0L -> {binding.secondPauseTr.setBackgroundResource(R.color.white)
                    breathViewModel.startTimer(getTimerPeriod())
                binding.seekBar.progress=0}
        }
    }
    fun getValueBreathe() {
        try {
            inhale = binding.inhaleTv.text.toString().toInt()
        } catch (e :Exception) {
            Toast.makeText(this.requireContext(),getString(R.string.errorMessage)+
                R.string.inhale,Toast.LENGTH_LONG).show()
        }
        try {
            exhale = binding.exhaleTv.text.toString().toInt()
        } catch (e :Exception) {
            Toast.makeText(this.requireContext(),getString(R.string.errorMessage)+
                    R.string.exhale,Toast.LENGTH_LONG).show()
        }
        try {
            firstPause = binding.firstPauseTv.text.toString().toInt()
            secondPause = binding.secondPauseTv.text.toString().toInt()
        } catch (e :Exception) {
            Toast.makeText(this.requireContext(),getString(R.string.errorMessage)+
                    R.string.pause,Toast.LENGTH_LONG).show()
        }
    }
    private fun getTimerPeriod():Long {
        return (inhale+exhale+firstPause+secondPause).toLong()+1
    }


}