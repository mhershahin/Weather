package com.service.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.service.entity.db.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LocationDao {

    @Query("SELECT * FROM locations ORDER BY id")
    abstract fun observeAll(): Flow<List<LocationEntity>>

    @Query("SELECT * FROM locations WHERE kind = :kind ORDER BY id")
    abstract fun observeByKind(kind: String): Flow<List<LocationEntity>>

    @Query("SELECT * FROM locations WHERE isCurrent = 1 LIMIT 1")
    abstract fun observeCurrent(): Flow<LocationEntity?>

    @Query("SELECT * FROM locations WHERE isGps = 1 LIMIT 1")
    abstract fun observeGps(): Flow<LocationEntity?>

    @Query("SELECT * FROM locations WHERE id = :id LIMIT 1")
    abstract suspend fun getById(id: Int): LocationEntity?

    @Query("SELECT COUNT(*) FROM locations")
    abstract suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsert(entity: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsertAll(entities: List<LocationEntity>)

    @Query("UPDATE locations SET isCurrent = 0")
    abstract suspend fun clearCurrentFlag(): Int

    @Query("UPDATE locations SET isCurrent = 1 WHERE id = :id")
    abstract suspend fun markCurrent(id: Int): Int

    @Query("UPDATE locations SET isGps = 0")
    abstract suspend fun clearGpsFlag(): Int

    @Query("UPDATE locations SET isGps = 1 WHERE id = :id")
    abstract suspend fun markGps(id: Int): Int

    @Delete
    abstract suspend fun delete(entity: LocationEntity): Int

    @Query("DELETE FROM locations WHERE id = :id")
    abstract suspend fun deleteById(id: Int): Int
}
