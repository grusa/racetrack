package earth.sochi.racetrack.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WorkoutTypeDao {
    @Query("SELECT id, name FROM workout_type_table ORDER BY id")
    fun getWorkoutTypes(): LiveData<WorkoutType>
    @Query("SELECT id, name FROM workout_type_table WHERE id = :id ORDER BY name")
    fun getWorkoutTypeById(id: Long): LiveData<List<WorkoutType>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(workoutsType: List<WorkoutType>)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkoutType(workoutsType: WorkoutType)
}