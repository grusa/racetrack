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
import earth.sochi.goyoga.GoYogaApplication
import earth.sochi.goyoga.databinding.FragmentHiitBinding
import earth.sochi.goyoga.viewmodels.HiitViewModel

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
        HiitViewModel.HiitViewModelFactory((this.activity?.application as GoYogaApplication).workoutTypeRepository)
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
        binding.backBt.setOnClickListener {
            view.findNavController().navigateUp()
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