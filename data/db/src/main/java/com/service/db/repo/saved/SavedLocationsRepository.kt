package com.service.db.repo.saved

import com.service.entity.domain.Location
import kotlinx.coroutines.flow.Flow

interface SavedLocationsRepository {
    fun observeSaved(): Flow<List<Location>>
    fun observeCurrent(): Flow<Location?>
    fun observeGps(): Flow<Location?>
    suspend fun save(location: Location)
    suspend fun remove(id: Int)
    suspend fun setCurrent(id: Int)
    suspend fun setGps(id: Int)
    suspend fun count(): Int
}
