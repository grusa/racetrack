package earth.sochi.racetrack.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import earth.sochi.racetrack.database.Workout
import earth.sochi.racetrack.database.WorkoutDao
import kotlinx.coroutines.launch

class WorkoutViewModel (
private val sleepNightKey: Long = 0L,
dataSource: WorkoutDao) : ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its SleepDatabaseDao.
     */
    private val database = dataSource

    private val workout: LiveData<Workout>

    init {
        workout=database.getAllWorkouts()
    }

    /**
     * Variable that tells the fragment whether it should navigate to [SleepTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _workouts = MutableLiveData<Workout?>()

    /**
     * When true immediately navigate back to the [SleepTrackerFragment]
     */
    val workouts: LiveData<Workout?>
    get() = _workouts

    // ?? fun getAllWorkouts() = workout
    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     */
    fun doneNavigating() {
        _workouts.value = null
    }

    fun onClose() {
        _workouts.value = null
    }
    fun run() {
        viewModelScope.launch {  }
    }

}