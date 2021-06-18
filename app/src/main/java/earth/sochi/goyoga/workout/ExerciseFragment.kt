package earth.sochi.goyoga.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import earth.sochi.goyoga.R
import earth.sochi.goyoga.RacetrackApplication
import earth.sochi.goyoga.databinding.FragmentExerciseBinding
import earth.sochi.goyoga.viewmodels.ExerciseViewModel

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
        binding.backBt.setOnClickListener {view.findNavController().navigateUp()}
        //Toast.makeText(activity,arguments?.getLong("hiitId").toString(),Toast.LENGTH_LONG).show()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) ={

            }
    }
}