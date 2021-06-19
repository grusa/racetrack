package earth.sochi.goyoga.workout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import earth.sochi.goyoga.R
import earth.sochi.goyoga.GoYogaApplication
import earth.sochi.goyoga.database.Weight
import earth.sochi.goyoga.databinding.FragmentWeightBinding
import earth.sochi.goyoga.viewmodels.WeightViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeightFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeightFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val TAG="WF"

//    public var weightList: List<Weight> = listOf()
    private lateinit var binding: FragmentWeightBinding
    val weightAdapter = WeightAdapter(this)
    private val weightViewModel: WeightViewModel by activityViewModels()
    {
        WeightViewModel.WeightViewModelFactory(
            (this.activity?.application as GoYogaApplication).workoutTypeRepository)
    }

    var lastWeight: Weight = Weight(0, 0, 0, 0)

    init {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_weight,container,false)
        binding = FragmentWeightBinding.bind(view)

        binding.downGrammBt.setOnClickListener(clickListener)
        binding.downKiloBt.setOnClickListener(clickListener)
        binding.upGrammBt.setOnClickListener(clickListener)
        binding.upKiloBt.setOnClickListener(clickListener)
        binding.buttonSave.setOnClickListener(saveClickListener)
        binding.backBt.setOnClickListener(clickListener)
        lifecycleScope.launchWhenCreated {
            lastWeight = weightViewModel.lastWeight()
            binding.grammTv.setText(lastWeight.weight_gramm.toString())
            binding.kiloTv.setText(lastWeight.weight_kilo.toString())
        }


        weightViewModel.allWeights.observe(viewLifecycleOwner,{
          it.let { weightAdapter.weights = it
          weightAdapter.notifyDataSetChanged()}
        })
        binding.listWeight.adapter = weightAdapter

        return view
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeightFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeightFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    val clickListener = View.OnClickListener { view ->
        when (view.getId()) {
            R.id.up_gramm_bt -> {
                if ((Integer.parseInt(binding.grammTv.text.toString())) < 999)
                    binding.grammTv.setText((Integer.parseInt(binding.grammTv.text.toString()) + 1).toString())
            }
            R.id.down_gramm_bt -> {
                if ((Integer.parseInt(binding.grammTv.text.toString())) > 0)
                    binding.grammTv.setText((Integer.parseInt(binding.grammTv.text.toString()) - 1).toString())
            }
            R.id.up_kilo_bt -> {
                if (Integer.parseInt(binding.kiloTv.text.toString()) < 150)
                    binding.kiloTv.setText((Integer.parseInt(binding.kiloTv.text.toString()) + 1).toString())
            }
            R.id.down_kilo_bt -> {
                if (Integer.parseInt(binding.kiloTv.text.toString()) > 0)
                    binding.kiloTv.setText((Integer.parseInt(binding.kiloTv.text.toString()) - 1).toString())
            }
            R.id.back_bt -> {
                view.findNavController().navigateUp()
            }
        }
    }

    val saveClickListener = View.OnClickListener {
        try {
            lastWeight = Weight(
                0, binding.kiloTv.text.toString().toInt(),
                binding.grammTv.text.toString().toInt(),
                System.currentTimeMillis()
            )
            weightViewModel.insertWeight(lastWeight)
        } catch (e: Exception) {
            Toast.makeText(this.context,R.string.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    fun deleteWeight(id:Long) {
        Log.d(TAG,"Delete ID $id")
        weightViewModel.deleteById(id)

//        weightViewModel.deleteById(id)
//        weightAdapter.notifyDataSetChanged()
    }

}
    class ChartDraw(context: Context?, attr: AttributeSet) : View(context, attr) {
        private val paint = Paint()

        override fun onDraw(canvas: Canvas) {
            drawXY(canvas)
            drawPoint(canvas, this.width.toFloat() / 2, this.height.toFloat() / 2)
            drawPoint(canvas, this.width.toFloat() / 3, this.height.toFloat() / 2)
            drawPoint(canvas, this.width.toFloat() / 4, this.height.toFloat() / 2)

            //TODO get weightList and make chart

        }

        private fun drawXY(canvas: Canvas) {
            paint.setColor(context.getColor(R.color.colorPrimaryDark))
            paint.setStrokeWidth(15f);
            canvas.drawLine(0F, 0F, 0F, this.height.toFloat(), paint) //Y-line ORDINATE
            canvas.drawLine(
                this.width.toFloat(),
                0F,
                this.width.toFloat(),
                this.height.toFloat(),
                paint
            ) //Y-line ORDINATE
            canvas.drawLine(
                0F,
                this.height.toFloat(),
                this.width.toFloat(),
                this.height.toFloat(),
                paint
            ) //X-line ABSCISSA
            canvas.drawLine(0F, 0F, this.width.toFloat(), 0F, paint) //X-line ABSCISSA
            paint.setStrokeWidth(5f);
            paint.setColor(context.getColor(R.color.colorSecondaryDark))
            canvas.drawLine(
                this.width.toFloat() / 2,
                0F,
                this.width.toFloat() / 2,
                this.height.toFloat(),
                paint
            ) //Y-line ORDINATE
            canvas.drawLine(
                this.width.toFloat() / 4,
                0F,
                this.width.toFloat() / 4,
                this.height.toFloat(),
                paint
            ) //Y-line ORDINATE
            canvas.drawLine(
                this.width.toFloat() * 3 / 4,
                0F,
                this.width.toFloat() * 3 / 4,
                this.height.toFloat(),
                paint
            ) //Y-line ORDINATE

            canvas.drawLine(
                0F,
                this.height.toFloat() / 2,
                this.width.toFloat(),
                this.height.toFloat() / 2,
                paint
            ) //X-line ABSCISSA
            canvas.drawLine(
                0F,
                this.height.toFloat() / 4,
                this.width.toFloat(),
                this.height.toFloat() / 4,
                paint
            ) //X-line ABSCISSA
            canvas.drawLine(
                0F,
                this.height.toFloat() * 3 / 4,
                this.width.toFloat(),
                this.height.toFloat() * 3 / 4,
                paint
            ) //X-line ABSCISSA
        }

        private fun drawPoint(canvas: Canvas, x: Float, y: Float) {
            paint.setColor(context.getColor(R.color.colorAccent))
            paint.setStrokeWidth(36f);
            canvas.drawPoint(x, y, paint)
        }

        private fun drawChart() {

        }
    }
