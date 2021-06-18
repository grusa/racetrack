package earth.sochi.goyoga.viewmodels

import androidx.lifecycle.*
import earth.sochi.goyoga.database.Workout
import earth.sochi.goyoga.database.WorkoutTypeRepository
import kotlinx.coroutines.launch


class WorkoutViewModel (
    private val repository : WorkoutTypeRepository
) : ViewModel() {
    val allWorkouts: LiveData<List<Workout>> = repository.allWorkouts.asLiveData()
    fun insertWorkout(workout: Workout) {
        viewModelScope.launch { repository.insertWorkout(workout) }
    }

    class WorkoutViewModelFactory(private val repository: WorkoutTypeRepository) :
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