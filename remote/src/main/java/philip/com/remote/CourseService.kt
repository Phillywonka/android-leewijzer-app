package org.buffer.android.boilerplate.remote

import io.reactivex.Flowable
import philip.com.remote.model.CourseModel
import retrofit2.http.GET

/**
 * Defines the abstract methods used for interacting with the Course API
 */
interface CourseService {

    @GET("team.json")
    fun getCourses(): Flowable<CourseResponse>

    class CourseResponse {
        lateinit var team: List<CourseModel>
    }

}