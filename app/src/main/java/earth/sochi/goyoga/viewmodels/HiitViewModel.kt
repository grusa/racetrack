package earth.sochi.goyoga.viewmodels

import androidx.lifecycle.*
import earth.sochi.goyoga.database.WorkoutTypeRepository
import kotlinx.coroutines.launch
import earth.sochi.goyoga.database.*

class HiitViewModel (private val repository : WorkoutTypeRepository)
        : ViewModel() {


        private var _hiits : LiveData<List<Hiit>> =
            repository.allHiits.asLiveData()

        val hiits : LiveData<List<Hiit>>
            get() = _hiits

        fun insertHiit(hiit: Hiit) {
            viewModelScope.launch { repository.insertHiit(hiit) }
        }
        fun loadHiitFromNetwork(){
            viewModelScope.launch { repository.loadHiitsFromSite()
                                    repository.loadExercisesFromSite()}
        }

        class HiitViewModelFactory(private val repository: WorkoutTypeRepository) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(HiitViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return HiitViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
