package earth.sochi.racetrack.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import earth.sochi.racetrack.R
import earth.sochi.racetrack.databinding.FragmentBreathBinding

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
    private var inhale: Int = 0
    private var firstPause: Int = 0
    private var exexhalation: Int = 0
    private var secondPause: Int = 0

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
        binding.buttonStart.setOnClickListener{
            onStartClick()
        }
        super.onViewCreated(view, savedInstanceState)
    }
    private fun onStartClick() {
        if (startStop) {
            binding.buttonStart.text = getText(R.string.stop)
            binding.inhaleTr.setBackgroundResource(R.color.colorAccent)
            binding.exhaleTr.setBackgroundResource(R.color.white)
        } else {
            binding.buttonStart.text = getText(R.string.start)
            binding.inhaleTr.setBackgroundResource(R.color.white)
            binding.exhaleTr.setBackgroundResource(R.color.colorAccent)

        }
        startStop = !startStop
    }


}