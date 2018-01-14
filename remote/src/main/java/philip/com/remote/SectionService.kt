package philip.com.remote

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.data.models.SectionEntity
import philip.com.remote.model.SectionModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Defines the abstract methods used for interacting with the Course API
 */
interface SectionService {

    @GET("/student/{student_number}/course/{course_name}/sections")
    fun getSections(@Path("student_number") studentNumber: String,
                    @Path("course_name") courseName: String): Flowable<SectionResponse>

    @POST("/student/{student}/sections")
    fun setSections(sections: List<SectionEntity>,
                    studentNumber: String,
                    courseName: String): Completable

    @GET("/student/{student}/courses")
    fun getCoursesForStudent(): Flowable<SectionResponse>

    class SectionResponse {
        lateinit var sections: List<SectionModel>
    }

}