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
open class GetSelectedCourses(private val courseRepository: CourseRepository,
                              threadExecutor: ThreadExecutor,
                              postExecutionThread: PostExecutionThread) :
        FlowableUseCase<List<Course>, String>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: String): Flowable<List<Course>> {
        return courseRepository.getCoursesForStudent(params)
    }

}