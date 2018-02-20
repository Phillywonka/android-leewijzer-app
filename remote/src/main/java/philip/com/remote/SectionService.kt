package philip.com.remote

import io.reactivex.Flowable
import philip.com.remote.model.SectionModel
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Defines the abstract methods used for interacting with the Course API
 */
interface SectionService {

    @GET("student/{student_number}/course/{course_name}/sections")
    fun getSections(@Path("student_number") studentNumber: String,
                    @Path("course_name") courseName: String): Flowable<SectionResponse>

    @POST("student/{student_number}/course/{course_name}/section/{section_id}")
    fun selectSection(@Path("student_number") studentNumber: String,
                      @Path("course_name") courseName: String,
                      @Path("section_id") sectionId: Int): Flowable<Int>

    @DELETE("student/{student_number}/section/{section_id}")
    fun deSelectSection(@Path("student_number") studentNumber: String,
                        @Path("section_id") sectionId: Int): Flowable<Int>

    @GET("course/{course_name}/sections")
    fun getAllSectionsForCourse(@Path("course_name") courseName: String): Flowable<SectionResponse>

    class SectionResponse {
        lateinit var sections: List<SectionModel>
    }

}