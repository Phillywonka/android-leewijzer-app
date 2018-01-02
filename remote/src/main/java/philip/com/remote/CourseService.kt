package philip.com.remote

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.remote.model.CourseModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Defines the abstract methods used for interacting with the Course API
 */
interface CourseService {

    @GET("/courses/{student_number}")
    fun getSelectedCourses(@Path("student_number") studentNumber: String): Flowable<CourseResponse>

    @GET("/courses")
    fun getAllCourses(): Flowable<CourseResponse>

    @POST("/student/{student_number}/course/{course_name}")
    fun addCourse(@Path("student_number") studentNumber: String,
                  @Path("course_name") courseName: String): Completable

    class CourseResponse {
        lateinit var courses: List<CourseModel>
    }

}