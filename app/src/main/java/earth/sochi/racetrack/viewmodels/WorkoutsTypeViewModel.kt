package earth.sochi.racetrack.viewmodels

import androidx.lifecycle.*
import earth.sochi.racetrack.database.WorkoutType
import earth.sochi.racetrack.database.WorkoutTypeDao
import kotlinx.coroutines.launch

class WorkoutsTypeViewModel (dataSource: WorkoutTypeDao)
        : ViewModel() {
        private val workoutType : LiveData<WorkoutType>
        private val _workoutTypes= MutableLiveData<WorkoutType>()
        init {
            if (dataSource.getWorkoutTypes()!=null) {
                suspend {
                    dataSource.insertWorkoutType(
                        WorkoutType(1, "No Types"))
                }
            }
            workoutType = dataSource.getWorkoutTypes()
        }

        val workoutTypes:LiveData<WorkoutType?>
        get() = _workoutTypes

    fun onClose() {
        _workoutTypes.value = null
    }
    fun run() {
        viewModelScope.launch {  }
    }
}