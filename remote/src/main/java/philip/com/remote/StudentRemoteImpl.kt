package philip.com.remote

import io.reactivex.Completable
import philip.com.data.repository.student.StudentRemote
import philip.com.remote.mapper.StudentEntityMapper
import javax.xml.ws.http.HTTPException

/**
 * @author Philip Wong
 * @since 09-03-18
 *
 * Remote implementation to log in a student. This class implements the
 * [StudentRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class StudentRemoteImpl(private val studentService: StudentService,
                        private val entityMapper: StudentEntityMapper) :
        StudentRemote {

    /**
     * Log in a student
     */
    override fun login(studentNumber: String, password: String): Completable {
        return try {
            studentService.login(studentNumber, password)
        } catch (error: HTTPException) {
            Completable.error(error)
        }
    }

}
