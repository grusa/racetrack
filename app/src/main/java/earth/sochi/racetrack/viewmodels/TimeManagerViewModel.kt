package earth.sochi.racetrack.viewmodels

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import earth.sochi.racetrack.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimeManagerViewModel (private val repository: WorkoutTypeRepository) : ViewModel() {

    private val REQUEST_CODE = 0
    private val TRIGGER_TIME = "TRIGGER_AT"

    private val minute: Long = 60_000L
    private val second: Long = 1_000L

    private var _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long>
        get() = _elapsedTime
    var startAlarm = MutableLiveData<Boolean>()
    private var timer: CountDownTimer?=null
    private var triggerTime=0L

    fun setTimeSelected(timerLengthSelection: Int) {
       // _timeSelection.value = (timerLengthSelection * second).toInt()
        _elapsedTime.value = timerLengthSelection.toLong() * second
    }
    fun resetTimer() {
        //button Reset pressed
        if (timer !=null) timer?.cancel()
    }
    fun stopTimer() {
        if (timer !=null) timer?.cancel()
        _elapsedTime.value = 0
        startAlarm.value=true
    }
    fun startTimer(timerLengthSelection: Long) {
        triggerTime = SystemClock.elapsedRealtime()+timerLengthSelection*second
        startAlarm.value = false
        createTimer()
    }
     private fun createTimer() {
        viewModelScope.launch {
            timer = object : CountDownTimer(triggerTime, second) {
                override fun onTick(millisUntilFinished: Long) {
                    _elapsedTime.value = (triggerTime - SystemClock.elapsedRealtime())/second
                    if (_elapsedTime.value!! <= 0) {
                        resetTimer()
                        stopTimer()
                    }
                }
                override fun onFinish() {
                    stopTimer()
                }
            }.start()
        }
    }
    class TimeManagerViewModelFactory(private val repository: WorkoutTypeRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TimeManagerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TimeManagerViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}