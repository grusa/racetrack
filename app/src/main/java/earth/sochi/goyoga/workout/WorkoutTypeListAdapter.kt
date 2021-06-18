package earth.sochi.goyoga.workout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import earth.sochi.goyoga.R
import earth.sochi.goyoga.database.WorkoutType


class WorkoutTypeListAdapter : ListAdapter<WorkoutType,
        WorkoutTypeListAdapter.WorkoutTypeViewHolder>(WorkoutTypeComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutTypeViewHolder {
        return WorkoutTypeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WorkoutTypeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name)
        holder.itemView.setOnClickListener(View.OnClickListener {
                runIntent(position,it)
         })
    }
    private fun runIntent(position: Int,view: View) {
        when (position) {
//            0 -> view.findNavController().navigate(R.id.action_rvFragment_to_stopwatchFragment)
//            1 -> view.findNavController().navigate(R.id.action_rvFragment_to_timerFragment)
            0 -> view.findNavController().navigate(R.id.action_rvFragment_to_metronomeFragment)
            1 -> view.findNavController().navigate(R.id.action_rvFragment_to_breathFragment)
            2 -> view.findNavController().navigate(R.id.action_rvFragment_to_runFragment)
            3 -> view.findNavController().navigate(R.id.action_rvFragment_to_hiitFragment)
            4 -> view.findNavController().navigate(R.id.action_rvFragment_to_weightFragment)
        }}
    class WorkoutTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val workoutTypeItemView: TextView = itemView.findViewById(R.id.tiktak_textView)

        fun bind(text: String?) {
            workoutTypeItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): WorkoutTypeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_experimental, parent, false)
                return WorkoutTypeViewHolder(view)
            }
        }
    }
    class WorkoutTypeComparator : DiffUtil.ItemCallback<WorkoutType>() {
        override fun areItemsTheSame(oldItem: WorkoutType, newItem: WorkoutType): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: WorkoutType, newItem: WorkoutType): Boolean {
            return oldItem.name == newItem.name
        }
    }
}