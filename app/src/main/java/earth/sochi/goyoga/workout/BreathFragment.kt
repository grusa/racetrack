package earth.sochi.goyoga.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import earth.sochi.goyoga.R
import earth.sochi.goyoga.GoYogaApplication
import earth.sochi.goyoga.databinding.FragmentBreathBinding
import earth.sochi.goyoga.viewmodels.BreathViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"


class BreathFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var inhale: Int = 4
    private var firstPause: Int = 1
    private var exhale: Int = 4
    private var secondPause: Int = 1
    private lateinit var binding : FragmentBreathBinding
    private var startStop:Boolean = true

    private val breathViewModel: BreathViewModel by activityViewModels() {
        BreathViewModel.BreathViewModelFactory((this.activity?.application as GoYogaApplication).workoutTypeRepository)
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
        binding.progressIndicatorSecondPause.setMax(100)

        binding.backBt.setOnClickListener{
            view.findNavController().navigateUp()
        }
        binding.buttonStart.setOnClickListener{
            onStartClick()
        }
        breathViewModel.elapsedTime.observe(viewLifecycleOwner, Observer {
            running(it)
        })
        super.onViewCreated(view, savedInstanceState)
    }
    private fun onStartClick() {
        if (startStop) {
            if (getValueBreathe()) {
                binding.buttonStart.text =getText(R.string.stop)
                binding.progressIndicatorSecondPause.setMax(getTimerPeriod().toInt())
                breathViewModel.createTimer(getTimerPeriod())
            }
        } else {
            binding.buttonStart.text = getText(R.string.start)
//            binding.inhaleTr.setBackgroundResource(R.color.colorPrimaryLightSuper)
//            binding.exhaleTr.setBackgroundResource(R.color.colorPrimaryLightSuper)
//            binding.firstPauseTr.setBackgroundResource(R.color.colorPrimaryLightSuper)
//            binding.secondPauseTr.setBackgroundResource(R.color.colorPrimaryLightSuper)
            breathViewModel.stopTimer()
        }
        startStop = !startStop
    }
    fun running(deal:Long) {
        when (deal) {
            (inhale+exhale+firstPause+secondPause).toLong() ->{
                                binding.inhaleTr.setBackgroundResource(R.color.colorAccent)}
            (exhale+firstPause+secondPause).toLong() -> {
                                binding.inhaleTr.setBackgroundResource(R.color.white)
                                binding.firstPauseTr.setBackgroundResource(R.color.colorAccent)}
            (exhale+secondPause).toLong() -> {
                                binding.firstPauseTr.setBackgroundResource(R.color.white)
                                binding.exhaleTr.setBackgroundResource(R.color.colorAccent)}
            (secondPause).toLong() -> {
                                binding.exhaleTr.setBackgroundResource(R.color.white)
                                binding.secondPauseTr.setBackgroundResource(R.color.colorAccent)}
            0L -> {
                                binding.secondPauseTr.setBackgroundResource(R.color.white)
                                if (!startStop) breathViewModel.createTimer(getTimerPeriod()) }
        }
        binding.progressIndicatorSecondPause.setProgress(
            binding.progressIndicatorSecondPause.getMax()-deal.toInt())

    }
    fun getValueBreathe():Boolean {
        try {
            inhale = binding.inhaleTv.text.toString().toInt()
        } catch (e :Exception) {
            Toast.makeText(this.requireContext(),getString(R.string.errorMessage)+
                R.string.inhale,Toast.LENGTH_LONG).show()
            return false
        }
        try {
            exhale = binding.exhaleTv.text.toString().toInt()
        } catch (e :Exception) {
            Toast.makeText(this.requireContext(),getString(R.string.errorMessage)+
                    R.string.exhale,Toast.LENGTH_LONG).show()
            return false
        }
        try {
            firstPause = binding.firstPauseTv.text.toString().toInt()
            secondPause = binding.secondPauseTv.text.toString().toInt()
        } catch (e :Exception) {
            Toast.makeText(this.requireContext(),getString(R.string.errorMessage)+
                    R.string.pause,Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
    private fun getTimerPeriod():Long {
        return (inhale+exhale+firstPause+secondPause).toLong()+1
    }


}