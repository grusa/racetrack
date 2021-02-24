package earth.sochi.racetrack.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import earth.sochi.racetrack.R
import earth.sochi.racetrack.databinding.FragmentListWorkoutBinding
import earth.sochi.racetrack.databinding.FragmentStopwatchBinding

class StopwatchFragment : Fragment() {
private  lateinit var binding:FragmentStopwatchBinding

var startStop:Boolean=true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentStopwatchBinding.bind(view)
        binding.stopwatchTv.text=getString(R.string.defaultTime)
        binding.buttonStart.setOnClickListener {startClick()}
        binding.buttonResume.setOnClickListener {resumeClick()}
        super.onViewCreated(view, savedInstanceState)
    }
    private fun startClick(){
        if (startStop) {
            binding.buttonStart.text = getString(R.string.stop)
            binding.stopwatchTv.start()
            startStop=false
        } else {
            binding.buttonStart.text = getString(R.string.start)
            binding.stopwatchTv.stop()
            startStop=true
        }
    }
    private fun resumeClick() {
        binding.buttonStart.text = getString(R.string.start)
        binding.stopwatchTv.stop()
        startStop=false
        binding.stopwatchTv.text=getString(R.string.defaultTime)
    }
}