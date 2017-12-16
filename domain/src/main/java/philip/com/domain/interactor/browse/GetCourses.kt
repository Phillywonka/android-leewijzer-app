package org.buffer.android.boilerplate.domain.interactor.browse

import io.reactivex.Flowable
import philip.com.domain.executor.PostExecutionThread
import org.buffer.android.boilerplate.domain.model.Course
import org.buffer.android.boilerplate.domain.repository.CourseRepository
import philip.com.domain.executor.ThreadExecutor
import philip.com.domain.interactor.FlowableUseCase

/**
 * Use case used for retrieving a [List] of [Course] instances from the [CourseRepository]
 */
open class GetCourses(private val courseRepository: CourseRepository,
                      threadExecutor: ThreadExecutor,
                      postExecutionThread: PostExecutionThread) :
        FlowableUseCase<List<Course>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Flowable<List<Course>> {
        return courseRepository.getCourses()
    }

}