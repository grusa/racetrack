package earth.sochi.racetrack.viewmodels

import androidx.lifecycle.*
import earth.sochi.racetrack.database.Workout
import earth.sochi.racetrack.database.WorkoutDao
import earth.sochi.racetrack.database.WorkoutRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow


class WorkoutViewModel (
    private val repository :WorkoutRepository
) : ViewModel() {
    val allWorkouts: LiveData<List<Workout>> = repository.allWorkouts.asLiveData()
    fun insertWorkout(workout: Workout) {
        viewModelScope.launch { repository.insert(workout) }
    }

    class WorkoutViewModelFactory(private val repository: WorkoutRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WorkoutViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}