package earth.sochi.goyoga.viewmodels

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.*
import earth.sochi.goyoga.database.*
import kotlinx.coroutines.*

class RunningViewModel (private val repository: WorkoutTypeRepository,
        val application: Application) : ViewModel() {

    private val REQUEST_CODE = 0
    private val TRIGGER_TIME = "TRIGGER_AT"

    private val minute: Long = 60_000L
    private val second: Long = 1_000L

    private var _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long>
        get() = _elapsedTime
    private var _metronomTime = MutableLiveData<Boolean>()
    val metronomTime: LiveData<Boolean>
        get() = _metronomTime
    var startAlarm = MutableLiveData<Boolean>()
    private var timer: CountDownTimer?=null
    private var triggerTime=0L
    private var runningStopwatch = false
    private val locationData = LocationLiveData(application)

    fun getLocationData() = locationData

    fun setTimeSelected(timerLengthSelection: Int) {
       // _timeSelection.value = (timerLengthSelection * second).toInt()
        _elapsedTime.value = timerLengthSelection.toLong() * second
    }
    fun resetTimer() {
        //button Reset pressed
        if (timer !=null) timer?.cancel()
    }
    fun startTimer(timerLengthSelection: Long) {
        triggerTime = System.currentTimeMillis()
        runningStopwatch = true
        createTimer()
    }
    fun stopTimer() {
        _elapsedTime.value = 0
        triggerTime = 0
        runningStopwatch = false
        viewModelScope.cancel()
    }
    private fun createTimer() {
        viewModelScope.launch {
            while(runningStopwatch ) {
                delay(5 * second)
                _elapsedTime.value = (System.currentTimeMillis()-triggerTime)/second
            }
        }
    }
    private fun createMetronom(beats:Int) {
        viewModelScope.launch {
            while (true) {
                _metronomTime.value = true
                delay((beats/60).toLong())
            }
        }
    }
    class RunningViewModelFactory(private val repository: WorkoutTypeRepository,
    val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RunningViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RunningViewModel(repository,application ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
