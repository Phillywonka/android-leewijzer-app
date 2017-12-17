package philip.com.data.source

import org.buffer.android.boilerplate.data.repository.CourseDataStore
import philip.com.data.repository.CourseCache

/**
 * Create an instance of a CourseDataStore
 */
open class CourseDataStoreFactory (
        private val courseCache: CourseCache,
        private val courseCacheDataStore: CourseCacheDataStore,
        private val courseRemoteDataStore: CourseRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): CourseDataStore {
        if (isCached && !courseCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): CourseDataStore {
        return courseCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): CourseDataStore {
        return courseRemoteDataStore
    }

}