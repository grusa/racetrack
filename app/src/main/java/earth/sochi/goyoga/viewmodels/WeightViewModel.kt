package earth.sochi.goyoga.viewmodels


import androidx.lifecycle.*
import earth.sochi.goyoga.database.*
import kotlinx.coroutines.launch


class WeightViewModel ( private val repository : WorkoutTypeRepository) : ViewModel() {
    private val _allWeights: LiveData<List<Weight>> = repository.allWeights.asLiveData()
    val allWeights : LiveData<List<Weight>>
        get() = _allWeights

    fun insertWeight(weight :Weight) {
        viewModelScope.launch { repository.insertWeight(weight) }
    }
    fun deleteById(id:Long) {
        viewModelScope.launch { repository.deleteWeightById(id) }
    }

    suspend fun lastWeight() :Weight = repository.lastWeight()

    class WeightViewModelFactory(private val repository: WorkoutTypeRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WeightViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WeightViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
