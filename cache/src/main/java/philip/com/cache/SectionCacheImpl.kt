package philip.com.cache

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.cache.database.CacheDatabase
import philip.com.cache.mapper.SectionEntityMapper
import philip.com.data.models.SectionEntity
import philip.com.data.repository.section.SectionCache

/**
 * Cached implementation for retrieving and saving Section instances. This class implements the
 * [SectionCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class SectionCacheImpl(val sectionsDatabase: CacheDatabase,
                       private val entityMapper: SectionEntityMapper,
                       private val preferencesHelper: PreferencesHelper) :
        SectionCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    /**
     * Retrieve an instance from the database, used for tests.
     */
    internal fun getDatabase(): CacheDatabase {
        return sectionsDatabase
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    override fun clearSections(): Completable {
        return Completable.defer {
            sectionsDatabase.cachedSectionDao().clearSections()
            Completable.complete()
        }
    }

    /**
     * Save the given list of [SectionEntity] instances to the database.
     */
    override fun saveSections(courses: List<SectionEntity>): Completable {
        return Completable.defer {
            courses.forEach {
                sectionsDatabase.cachedSectionDao().insertSection(
                        entityMapper.mapToCached(it))
            }
            Completable.complete()
        }
    }

//    override fun getAllSections(): Flowable<List<SectionEntity>> {
//        return Flowable.defer {
//            Flowable.just(sectionsDatabase.cachedSectionDao().loadSelectedSections())
//        }.map {
//            it.map { entityMapper.mapFromCached(it) }
//        }
//    }

    /**
     * Retrieve a list of [SectionEntity] instances from the database.
     */
    override fun getSections(): Flowable<List<SectionEntity>> {
        Log.d("Application", "SectionCacheImpl: get courses: ")
        return Flowable.defer {
            Flowable.just(sectionsDatabase.cachedSectionDao().getSelectedSections())
        }.map {
            it.map { entityMapper.mapFromCached(it) }
        }
    }

    /**
     * Check whether there are instances of [CachedSection] stored in the cache.
     */
    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Log.d("Application", "SectionCacheImpl: isCached: " + sectionsDatabase.cachedSectionDao().getSelectedSections().isNotEmpty())
            Single.just(sectionsDatabase.cachedSectionDao().getSelectedSections().isNotEmpty())
        }
    }

    /**
     * Set a point in time at when the cache was last updated.
     */
    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time.
     */
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

}