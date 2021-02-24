 package earth.sochi.racetrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import earth.sochi.racetrack.database.WorkoutType
import earth.sochi.racetrack.workout.WorkoutTypeListAdapter
import earth.sochi.racetrack.viewmodels.WorkoutViewModel
import earth.sochi.racetrack.viewmodels.WorkoutTypeViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RvFragment : Fragment() {
    private val workoutTypeViewModel: WorkoutTypeViewModel by viewModels ()
    {
        WorkoutTypeViewModel.WorkoutTypeViewModelFactory(
            (this.activity?.application as RacetrackApplication).workoutTypeRepository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_rv, container, false)
        val recyclerView: RecyclerView =view.findViewById(R.id.list_recyclerview)
        val tikTakAdapter = WorkoutTypeListAdapter()
        recyclerView.adapter = tikTakAdapter
        workoutTypeViewModel.workoutTypes.observe( viewLifecycleOwner) {
                workoutTypes ->
            // Update the cached copy of the words in the adapter.
            workoutTypes.let { tikTakAdapter.submitList(it) }
        }
//        val itemDecorator =
//            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
//        itemDecorator.setDrawable(
//            ContextCompat.getDrawable(recyclerView.context,
//                R.drawable.twcolor)!!)
//        recyclerView.addItemDecoration(itemDecorator)
        return view //inflater.inflate(R.layout.fragment_rv, container, false)
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RvFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RvFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}