package com.service.db.repo.saved

import com.service.db.dao.LocationDao
import com.service.entity.db.LocationEntity
import com.service.entity.domain.Location
import com.service.entity.domain.LocationKind
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavedLocationsRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
) : SavedLocationsRepository {

    override fun observeSaved(): Flow<List<Location>> =
        locationDao.observeByKind(LocationKind.SAVED.name).map { list -> list.map { it.toDomain() } }


    override fun observeCurrent(): Flow<Location?> =
        locationDao.observeCurrent().map { it?.toDomain() }

    override fun observeGps(): Flow<Location?> =
        locationDao.observeGps().map { it?.toDomain() }

    override suspend fun save(location: Location) {
        val existing = locationDao.getById(location.id)
        val merged = location.copy(
            kind = LocationKind.SAVED,
            isCurrent = location.isCurrent || (existing?.isCurrent ?: false),
            isGps = location.isGps || (existing?.isGps ?: false),
        )
        locationDao.upsert(LocationEntity.fromDomain(merged))
    }

    override suspend fun remove(id: Int) {
        locationDao.deleteById(id)
    }

    override suspend fun setCurrent(id: Int) {
        locationDao.clearCurrentFlag()
        locationDao.markCurrent(id)
    }

    override suspend fun setGps(id: Int) {
        locationDao.clearGpsFlag()
        locationDao.markGps(id)
    }
    override suspend fun count(): Int = locationDao.count()
}
