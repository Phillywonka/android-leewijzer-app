package philip.com.data.source.student

import philip.com.data.repository.student.StudentCache
import philip.com.data.repository.student.StudentDatastore

/**
 * Create an instance of a StudentDataStore
 */
open class StudentDataStoreFactory(
        private val studentCache: StudentCache,
        private val studentCacheDataStore: StudentCacheDataStore,
        private val studentRemoteDataStore: StudentRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): StudentDatastore {
        if (isCached && !studentCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): StudentDatastore {
        return studentCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): StudentDatastore {
        return studentRemoteDataStore
    }

}