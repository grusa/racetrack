/*
 * Copyright (C) 2020 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package earth.sochi.goyoga.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Defines database operations.
 */
@Dao
interface MyLocationDao {
    @Query("SELECT id, latitude, longitude, foreground, date FROM my_location_table ORDER BY date DESC")
    fun getLocations(): Flow<List<MyLocationEntity>>
//    id, latitude, longitude, foreground, date

    @Query("SELECT id, latitude, longitude, foreground, date FROM my_location_table WHERE id=(:id)")
    fun getLocation(id: Long): Flow<MyLocationEntity>

    @Update
    suspend fun updateLocation(myLocationEntity: MyLocationEntity)

    @Insert
    suspend fun addLocation(myLocationEntity: MyLocationEntity)

    @Insert
    suspend fun addLocations(myLocationEntities: List<MyLocationEntity>)
    @Query("DELETE FROM my_location_table")
    suspend fun deleteAll()
}
