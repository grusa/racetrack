package earth.sochi.racetrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import earth.sochi.racetrack.databinding.FragmentListWorkoutBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ListWorkoutFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.
        inflate<FragmentListWorkoutBinding>(inflater,R.layout.fragment_list_workout,container,false)
        binding.testBt.setOnClickListener(){
            //it.findNavController().navigate(R.id.action_rvFragment_to_stopwatchFragment)

        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}