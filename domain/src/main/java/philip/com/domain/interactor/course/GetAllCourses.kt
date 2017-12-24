package philip.com.domain.interactor.course

import io.reactivex.Flowable
import philip.com.domain.executor.PostExecutionThread
import philip.com.domain.executor.ThreadExecutor
import philip.com.domain.interactor.FlowableUseCase
import philip.com.domain.model.Course
import philip.com.domain.repository.CourseRepository

/**
 * Use case used for retrieving a [List] of [Course] instances from the [CourseRepository]
 */
open class GetAllCourses(private val courseRepository: CourseRepository,
                         threadExecutor: ThreadExecutor,
                         postExecutionThread: PostExecutionThread) :
        FlowableUseCase<List<Course>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Flowable<List<Course>> {
        return courseRepository.getCourses()
    }

}