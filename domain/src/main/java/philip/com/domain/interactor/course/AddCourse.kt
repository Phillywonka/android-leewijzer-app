package philip.com.domain.interactor.course

import io.reactivex.Completable
import philip.com.domain.executor.PostExecutionThread
import philip.com.domain.executor.ThreadExecutor
import philip.com.domain.interactor.CompletableUseCase
import philip.com.domain.model.Course
import philip.com.domain.repository.CourseRepository

/**
 * Use case used for retrieving a [List] of [Course] instances from the [CourseRepository]
 */
open class AddCourse(private val courseRepository: CourseRepository,
                     threadExecutor: ThreadExecutor,
                     postExecutionThread: PostExecutionThread) :
        CompletableUseCase<List<Course>>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: List<Course>): Completable {
        return courseRepository.saveCourses(params)
    }

}