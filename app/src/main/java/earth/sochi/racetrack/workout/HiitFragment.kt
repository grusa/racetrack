package earth.sochi.racetrack.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
import earth.sochi.racetrack.databinding.FragmentHiitBinding
import earth.sochi.racetrack.viewmodels.HiitViewModel
import earth.sochi.racetrack.viewmodels.WorkoutTypeViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HiitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HiitFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var  binding : FragmentHiitBinding
    private val hiitViewModel : HiitViewModel by activityViewModels() {
        HiitViewModel.HiitViewModelFactory((this.activity?.application as RacetrackApplication).workoutTypeRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_hiit, container, false)
        val hiitAdapter = HiitAdapter()
        binding = FragmentHiitBinding.bind(view)
        binding.hiitRv.adapter = hiitAdapter
        hiitViewModel.hiits.observe(viewLifecycleOwner) {
            hiits -> hiits.let { hiitAdapter.submitList(it)
            hiitAdapter.notifyDataSetChanged()}
        }
        val itemDecorator =
            DividerItemDecoration(binding.hiitRv.context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(binding.hiitRv.context,
                R.drawable.twcolor)!!)
        binding.hiitRv.addItemDecoration(itemDecorator)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pauseBt.setOnClickListener{
            hiitViewModel.loadHiitFromNetwork()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HiitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}