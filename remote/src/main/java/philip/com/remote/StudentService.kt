package philip.com.remote

import io.reactivex.Completable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author Philip Wong
 * @since 09-03-18
 **/
interface StudentService {

    @POST("login/{student_number}/{first_name}/{last_name}/{password}")
    fun register(@Path("student_number") studentNumber: String,
              @Path("first_name") firstName: String,
              @Path("last_name") lastName: String,
              @Path("password") password: String): Completable

    @GET("login/{student_number}/{password}")
    fun login(@Path("student_number") studentNumber: String,
              @Path("password") password: String): Completable
}
