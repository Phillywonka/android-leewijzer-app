package philip.com.remote

import io.reactivex.Completable
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author Philip Wong
 * @since 09-03-18
 **/
interface StudentService {

    @POST("login/{student_number}/{student_password}")
    fun login(@Path("student_number") studentNumber: String,
              @Path("password") password: String): Completable
}
