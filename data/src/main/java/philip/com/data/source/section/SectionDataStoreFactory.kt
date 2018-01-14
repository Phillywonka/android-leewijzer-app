package philip.com.data.source.section

import philip.com.data.repository.section.SectionCache
import philip.com.data.repository.section.SectionDataStore

/**
 * Create an instance of a SectionDataStore
 */
open class SectionDataStoreFactory(
        private val sectionCache: SectionCache,
        private val sectionCacheDataStore: SectionCacheDataStore,
        private val sectionRemoteDataStore: SectionRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): SectionDataStore {
        if (isCached && !sectionCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): SectionDataStore {
        return sectionCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): SectionDataStore {
        return sectionRemoteDataStore
    }

}