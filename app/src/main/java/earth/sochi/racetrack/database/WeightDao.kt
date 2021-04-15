package earth.sochi.racetrack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface WeightDao {

    @Query("SELECT id, weight_kilo, weight_gramm,date  FROM weight_table ORDER BY id")
    fun getWeights(): Flow<List<Weight>>

    @Query("SELECT id, weight_kilo, weight_gramm,date  FROM weight_table ORDER BY id DESC LIMIT 1")
    suspend fun lastWeight(): Weight

    @Query("SELECT id, weight_kilo, weight_gramm,date FROM weight_table WHERE id = :id ORDER BY id")
    fun getWeightById(id: Long): Flow<List<Weight>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(weights: List<Weight>)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weight: Weight)
    @Query("DELETE FROM weight_table")
    suspend fun deleteAll()
}
