package earth.sochi.racetrack.viewmodels

import androidx.lifecycle.*
import earth.sochi.racetrack.database.WorkoutType
import earth.sochi.racetrack.database.WorkoutTypeRepository
import kotlinx.coroutines.launch
import earth.sochi.racetrack.database.*
import kotlinx.coroutines.launch

    class ExerciseViewModel (private val repository : WorkoutTypeRepository)
        : ViewModel() {


        private var _exercises : LiveData<List<Exercise>> =
            repository.allExercises().asLiveData()

        val exercises : LiveData<List<Exercise>>
            get() = _exercises

        fun insertExercise(exercise: Exercise) {
            viewModelScope.launch { repository.insertExercise(exercise) }
        }
        fun insertExercises(exercises: List<Exercise>) {
            viewModelScope.launch { repository.insertExercises(exercises) }
        }

        fun getExercisesByHiitId(id:Long):LiveData<List<Exercise>> {
            return repository.allExercisesByHiitId(id).asLiveData()
        }

        class ExerciseViewModelFactory(private val repository: WorkoutTypeRepository) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ExerciseViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
