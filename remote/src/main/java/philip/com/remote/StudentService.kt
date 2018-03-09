package philip.com.remote

import io.reactivex.Completable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Philip Wong
 * @since 09-03-18
 **/
interface StudentService {

    @GET("login/{student_number}/{password}")
    fun login(@Path("student_number") studentNumber: String,
              @Path("password") password: String): Completable
}
