package philip.com.remote

import io.reactivex.Flowable
import philip.com.remote.model.CourseModel
import retrofit2.http.GET

/**
 * Defines the abstract methods used for interacting with the Course API
 */
interface CourseService {

    @GET("/showCourses.php")
    fun getSelectedCourses(studentNumber: String): Flowable<CourseResponse>

    @GET("/showCourses.php")
    fun getAllCourses(): Flowable<CourseResponse>

    @GET("/student/{student}/course/{courses")
    fun getCoursesForStudent(): Flowable<CourseResponse>

    class CourseResponse {
        lateinit var courses: List<CourseModel>
    }

}