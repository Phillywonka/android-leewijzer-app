package org.buffer.android.boilerplate.data.repository

import io.reactivex.Flowable
import philip.com.data.models.CourseEntity

/**
 * Interface defining methods for the caching of Courses. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface CourseRemote {

    /**
     * Retrieve a list of Courses, from the cache
     */
    fun getCourses(): Flowable<List<CourseEntity>>

}