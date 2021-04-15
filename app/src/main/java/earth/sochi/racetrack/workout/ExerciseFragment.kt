package earth.sochi.racetrack.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import earth.sochi.racetrack.R
import earth.sochi.racetrack.RacetrackApplication
import earth.sochi.racetrack.database.Hiit
import earth.sochi.racetrack.database.WorkoutTypeRepository
import earth.sochi.racetrack.databinding.FragmentExerciseBinding
import earth.sochi.racetrack.databinding.FragmentHiitBinding
import earth.sochi.racetrack.viewmodels.ExerciseViewModel
import earth.sochi.racetrack.viewmodels.HiitViewModel
import earth.sochi.racetrack.viewmodels.WorkoutTypeViewModel

class ExerciseFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var  binding : FragmentExerciseBinding
    private val exerciseViewModel : ExerciseViewModel by activityViewModels() {
        ExerciseViewModel.ExerciseViewModelFactory((this.activity?.application as RacetrackApplication).workoutTypeRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_exercise, container, false)
        val exerciseAdapter = ExerciseAdapter()
        binding = FragmentExerciseBinding.bind(view)
        binding.exerciseRv.adapter = exerciseAdapter
        exerciseViewModel.getExercisesByHiitId(arguments?.getLong("hiitId")?:0).observe(viewLifecycleOwner) {
            it -> it.let { exerciseAdapter.submitList(it)
            exerciseAdapter.notifyDataSetChanged()}
        }
        val itemDecorator =
            DividerItemDecoration(binding.exerciseRv.context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(binding.exerciseRv.context,
                R.drawable.twcolor)!!)
        binding.exerciseRv.addItemDecoration(itemDecorator)
        //Toast.makeText(activity,arguments?.getLong("hiitId").toString(),Toast.LENGTH_LONG).show()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pauseBt.setOnClickListener{

        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) ={

            }
    }
}