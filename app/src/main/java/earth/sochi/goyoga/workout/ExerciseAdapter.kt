package earth.sochi.goyoga.workout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import earth.sochi.goyoga.R
import earth.sochi.goyoga.database.Exercise
import coil.load

class ExerciseAdapter : ListAdapter<Exercise,
            ExerciseAdapter.ExerciseViewHolder>(ExerciseComparator()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
            return ExerciseViewHolder.create(parent)
        }

        override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
            val current = getItem(position)
            holder.bind(current.name,current.duration.toString(),current.imageurl)
            holder.itemView.setOnClickListener{
                runIntend(current.id,it)
            }
        }
        fun runIntend(hiitId:Long,view:View) {
            view.findNavController().navigate(R.id.action_hiitFragment_to_exerciseFragment)
        }
        class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val nameItemView: TextView = itemView.findViewById(R.id.hiit_name)
            private val durationItemView: TextView = itemView.findViewById(R.id.hiit_duration)
            private val imageView:ImageView = itemView.findViewById(R.id.hiit_image)

            fun bind(name: String?,duration:String?,url:String) {
                nameItemView.text = name
                durationItemView.text = duration
                imageView.load("https://$url")
            }

            companion object {
                fun create(parent: ViewGroup): ExerciseViewHolder {
                    val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_hiit, parent, false)
                    return ExerciseViewHolder(view)
                }
            }
        }
        class ExerciseComparator : DiffUtil.ItemCallback<Exercise>() {
            override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
