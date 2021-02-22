package earth.sochi.racetrack.workout

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import earth.sochi.racetrack.R
import earth.sochi.racetrack.database.WorkoutType
import java.util.stream.Collectors.toMap
//class TikTakAdapter (private val dataSet: List<WorkoutType>) :
class TikTakAdapter (private val dataSet: MutableMap<Int,String>) :
        RecyclerView.Adapter<TikTakAdapter.ViewHolder>() {
        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.tiktak_textView)
        }
        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.row_experimental, viewGroup, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.textView.text = dataSet[position]
            viewHolder.textView.setOnClickListener(View.OnClickListener {
                runIntent(position,it)
            })
        }
        override fun getItemCount() = dataSet.size
        private fun runIntent(position: Int,view: View) {
            if (position==0) {
                Navigation.findNavController(view).navigate(R.id.action_rvFragment_to_stopwatchFragment)
            } else if (position==1) {
                view.findNavController().navigate(R.id.action_rvFragment_to_timerFragment)

            }
        }
    }
