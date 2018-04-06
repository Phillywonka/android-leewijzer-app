package philip.com.domain.interactor.authentication

import io.reactivex.Completable
import philip.com.domain.executor.PostExecutionThread
import philip.com.domain.executor.ThreadExecutor
import philip.com.domain.interactor.CompletableUseCase
import philip.com.domain.repository.StudentRepository

/**
 * @author Philip Wong
 * @since 23-12-17
 **/
open class Register(private val studentRepository: StudentRepository,
                    threadExecutor: ThreadExecutor,
                    postExecutionThread: PostExecutionThread) :
        CompletableUseCase<HashMap<String, String>>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: HashMap<String, String>): Completable {
        return studentRepository.askForLogin(
                params.getValue("student_number"),
                params.getValue("password")
        )
    }

}
