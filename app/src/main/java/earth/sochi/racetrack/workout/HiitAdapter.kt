package earth.sochi.racetrack.workout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import earth.sochi.racetrack.R
import earth.sochi.racetrack.database.Hiit
import earth.sochi.racetrack.database.WorkoutType

class HiitAdapter : ListAdapter<Hiit,
            HiitAdapter.HiitViewHolder>(HiitComparator()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiitViewHolder {
            return HiitViewHolder.create(parent)
        }

        override fun onBindViewHolder(holder: HiitViewHolder, position: Int) {
            val current = getItem(position)
            holder.bind(current.name,current.duration.toString())
            holder.itemView.setOnClickListener{
                runIntend(current.id,it)
            }
        }
        fun runIntend(hiitId:Long,view :View) {
            //TODO add ID
            val bundle = bundleOf("hiitId" to hiitId)
            view.findNavController().navigate(R.id.action_hiitFragment_to_exerciseFragment,bundle)
        }
        class HiitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val nameItemView: TextView = itemView.findViewById(R.id.hiit_name)
            private val durationItemView: TextView = itemView.findViewById(R.id.hiit_duration)

            fun bind(name: String?,duration:String?) {
                nameItemView.text = name
                durationItemView.text = duration
            }

            companion object {
                fun create(parent: ViewGroup): HiitViewHolder {
                    val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_hiit, parent, false)
                    return HiitViewHolder(view)
                }
            }
        }
        class HiitComparator : DiffUtil.ItemCallback<Hiit>() {
            override fun areItemsTheSame(oldItem: Hiit, newItem: Hiit): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Hiit, newItem: Hiit): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
