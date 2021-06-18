package earth.sochi.goyoga.workout

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import earth.sochi.goyoga.R
import earth.sochi.goyoga.database.Weight
import earth.sochi.goyoga.getDate

class WeightAdapter(fragment:WeightFragment):  RecyclerView.Adapter<WeightAdapter.WeightViewHolder>() {
    val fragment = fragment
    var  weights : List<Weight> = emptyList()
    val TAG = "WeightAdapter"


    class WeightViewHolder(view : View)
        :RecyclerView.ViewHolder(view){
        val weightKiloTv:TextView
        val weightGrammTv:TextView
        val weightDateTv:TextView
        init {
            // Define click listener for the ViewHolder's View.
            weightKiloTv = view.findViewById(R.id.weight_kilo)
            weightGrammTv = view.findViewById(R.id.weight_gramm)
            weightDateTv = view.findViewById(R.id.date)
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeightViewHolder {
        val view:View = LayoutInflater.from (parent.context)
        .inflate(R.layout.row_weight,parent,false)
        return WeightViewHolder(view)
    }
    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        holder.weightKiloTv.text = weights[position].weight_kilo.toString()
        holder.weightGrammTv.text = weights[position].weight_gramm.toString()
        holder.weightDateTv.text = getDate(weights[position].date)

        holder.itemView.setOnLongClickListener(
        {
            Log.d(TAG,"Deleted ${weights[position].id} $${getDate(weights[position].date)}")
            fragment.deleteWeight(weights[position].id)
            return@setOnLongClickListener true})

    }
    fun getIdAtPosition(position: Int): Long {
        return weights[position].id
    }
    override fun getItemCount(): Int {
        return weights.size
    }
}
