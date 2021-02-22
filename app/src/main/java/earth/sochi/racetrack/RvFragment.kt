 package earth.sochi.racetrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import earth.sochi.racetrack.database.WorkoutType
import earth.sochi.racetrack.workout.TikTakAdapter
import earth.sochi.racetrack.viewmodels.WorkoutViewModel
import earth.sochi.racetrack.viewmodels.WorkoutsTypeViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RvFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RvFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var elementList=  mutableMapOf<Int,String>()
    private lateinit var workoutsTypeViewModel: WorkoutsTypeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var wt =WorkoutType (0,"StopWatch")
        var wts = listOf (wt )


        elementList[0] = "StopWatch"
        elementList[1] ="Timer"
        elementList[2] ="Metronome"
        elementList[3] ="Breathing"
        elementList[4] ="Pranayama"
        elementList[5] ="Running"
        elementList[6] ="Walking"

        workoutsTypeViewModel=
            ViewModelProvider(this).get(WorkoutsTypeViewModel::class.java)

        val tikTakAdapter = TikTakAdapter(elementList)
        val view = inflater!!.inflate(R.layout.fragment_rv, container, false)
        val recyclerView: RecyclerView =view.findViewById(R.id.list_recyclerview)

        recyclerView.adapter = tikTakAdapter
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