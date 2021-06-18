package earth.sochi.goyoga.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutTypeDao {
    @Query("SELECT id, name FROM workout_type_table ORDER BY id")
    fun getWorkoutTypes(): Flow<List<WorkoutType>>
    @Query("SELECT id, name FROM workout_type_table WHERE id = :id ORDER BY name")
    fun getWorkoutTypeById(id: Long): Flow<List<WorkoutType>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(workoutsType: List<WorkoutType>)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workoutsType: WorkoutType)
    @Query("DELETE FROM workout_type_table")
    suspend fun deleteAll()
}