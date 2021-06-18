package earth.sochi.goyoga.workout

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import earth.sochi.goyoga.R
import earth.sochi.goyoga.databinding.FragmentStopwatchBinding

class StopwatchFragment : Fragment() {
    private  lateinit var binding:FragmentStopwatchBinding
    var startStop:Boolean=true
    var time = SystemClock.elapsedRealtime()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentStopwatchBinding.bind(view)
        binding.buttonStart.setOnClickListener {startClick()}
        binding.buttonResume.visibility = View.INVISIBLE
        binding.buttonResume.setOnClickListener {resumeClick()}
        time = 0L

        super.onViewCreated(view, savedInstanceState)
    }
    private fun startClick(){
//        val animator = ObjectAnimator.ofFloat(binding.arrowImage, View.ROTATION, -360f, 0f)
        if (startStop) {
            binding.buttonStart.text = getString(R.string.stop)
            binding.stopwatchTv.start()
            if (time == 0L) binding.stopwatchTv.setBase (SystemClock.elapsedRealtime())
                else binding.stopwatchTv.setBase(time.toLong())
            binding.buttonResume.visibility = View.INVISIBLE
            //Animation
//            animator.duration = 60_000
//            animator.repeatCount = 50
//            animator.start()
            //Animation
            startStop=false
        } else {
            binding.buttonStart.text = getString(R.string.start)
            binding.stopwatchTv.stop()
            time = binding.stopwatchTv.getBase()
//            animator.pause()
            binding.buttonResume.visibility = View.VISIBLE
            startStop=true
        }
    }
    private fun resumeClick() {
        binding.buttonStart.text = getString(R.string.start)
        binding.stopwatchTv.stop()
        //binding.stopwatchTv.text=getString(R.string.defaultTime)
        time = 0L
        binding.buttonResume.visibility = View.INVISIBLE
        binding.stopwatchTv.setBase(SystemClock.elapsedRealtime());
    }
}