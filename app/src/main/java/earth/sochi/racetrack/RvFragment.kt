 package earth.sochi.racetrack

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import earth.sochi.racetrack.database.WorkoutType
import earth.sochi.racetrack.workout.WorkoutTypeListAdapter
import earth.sochi.racetrack.viewmodels.WorkoutViewModel
import earth.sochi.racetrack.viewmodels.WorkoutTypeViewModel
import java.security.Permission

 private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RvFragment : Fragment() {
    private val workoutTypeViewModel: WorkoutTypeViewModel by activityViewModels ()
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
        val itemDecorator =
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(recyclerView.context,
                R.drawable.twcolor)!!)
        recyclerView.addItemDecoration(itemDecorator)
        checkPermission()
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
    fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION) -> {
            Toast.makeText(activity,"In Running module calculated distance",Toast.LENGTH_LONG).show()
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            //showInContextUI(...)
        }   else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
//            requestPermissions(CONTEXT,
//                arrayOf(Manifest.permission.REQUESTED_PERMISSION),
//                REQUEST_CODE)
            }
        }
    }
}