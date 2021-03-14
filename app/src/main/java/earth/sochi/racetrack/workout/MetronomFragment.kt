package earth.sochi.racetrack.workout

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import earth.sochi.racetrack.R
import earth.sochi.racetrack.RacetrackApplication
import earth.sochi.racetrack.databinding.FragmentMetronomeBinding
import earth.sochi.racetrack.toSeconds
import earth.sochi.racetrack.utils.MetronomService
import earth.sochi.racetrack.viewmodels.TimeManagerViewModel

class MetronomeFragment : Fragment() {
    private lateinit var binding : FragmentMetronomeBinding
    private var startStop:Boolean=true
    private val timeManagerViewModel: TimeManagerViewModel by activityViewModels() {
        TimeManagerViewModel.TimeManagerViewModelFactory((this.activity?.application as RacetrackApplication).workoutTypeRepository)
    }
    private var timer :Int=0
    var running:kotlin.Boolean = false
    var serviceBound:kotlin.Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_metronome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMetronomeBinding.bind(view)
        binding.upSecondBt.setOnClickListener(clickListener)
        binding.downSecondBt.setOnClickListener(clickListener)
        binding.buttonStart.setOnClickListener {startClick()}

    }
    val clickListener = View.OnClickListener {view ->
        when (view.getId()) {
            R.id.up_second_bt -> { if ((Integer.parseInt(binding.secondsTv.text.toString())) < 120)
                binding.secondsTv.setText((Integer.parseInt(binding.secondsTv.text.toString()) + 1).toString())
            }
            R.id.down_second_bt -> { if ((Integer.parseInt(binding.secondsTv.text.toString())) > 0)
                binding.secondsTv.setText ((Integer.parseInt(binding.secondsTv.text.toString())-1).toString())
            }
        }
    }
    fun startClick() {
        if (startStop) {
            binding.buttonStart.text = getText(R.string.stop)
            startStop = false
            try {
                timer = Integer.valueOf(60000 / Integer.valueOf(binding.secondsTv.getText().toString()))
                //timer = 50000; //TODO timer calculation
            } catch (e: java.lang.Exception) {
                Toast.makeText(requireContext(), "Please set correct BPM", Toast.LENGTH_LONG).show()
                binding.secondsTv.setText("40")
            }
            try {
                val intent = Intent(this.requireContext(), earth.sochi.racetrack.utils.MetronomService::class.java)
                intent.putExtra("timer", timer)
                requireActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            } catch (e: Exception) {
                Toast.makeText(this.requireContext(), "Intent:$e", Toast.LENGTH_LONG).show()
            }
        } else {
            binding.buttonStart.text = getText(R.string.start)
            startStop = true
            requireActivity().unbindService(serviceConnection)
        }
    }
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            serviceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceBound = false
        }
    }
}