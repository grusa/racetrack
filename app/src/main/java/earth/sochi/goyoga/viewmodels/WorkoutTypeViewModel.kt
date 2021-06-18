package earth.sochi.goyoga.viewmodels

import androidx.lifecycle.*
import earth.sochi.goyoga.database.*
import kotlinx.coroutines.launch

class WorkoutTypeViewModel (private val repository : WorkoutTypeRepository)
        : ViewModel() {

        private val _workoutTypes : LiveData<List<WorkoutType>> =
            repository.allWorkoutTypes.asLiveData()

        val workoutTypes :LiveData<List<WorkoutType>>
        get() = _workoutTypes

    fun insertWorkoutType(workoutType: WorkoutType) {
        viewModelScope.launch { repository.insert(workoutType) }
    }

    class WorkoutTypeViewModelFactory(private val repository: WorkoutTypeRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WorkoutTypeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WorkoutTypeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}