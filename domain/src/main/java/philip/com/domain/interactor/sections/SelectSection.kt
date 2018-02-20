package philip.com.domain.interactor.sections

import io.reactivex.Flowable
import philip.com.domain.executor.PostExecutionThread
import philip.com.domain.executor.ThreadExecutor
import philip.com.domain.interactor.FlowableUseCase
import philip.com.domain.model.Course
import philip.com.domain.repository.CourseRepository
import philip.com.domain.repository.SectionRepository

/**
 * Use case used for retrieving a [List] of [Course] instances from the [CourseRepository]
 */
open class SelectSection(private val sectionRepository: SectionRepository,
                         threadExecutor: ThreadExecutor,
                         postExecutionThread: PostExecutionThread) :
        FlowableUseCase<Int, HashMap<String, Any>>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: HashMap<String, Any>): Flowable<Int> {
        return sectionRepository.selectSection(
                params["student_number"]!! as String,
                params["course_name"]!! as String,
                params["section_id"]!! as Int,
                params["selected"]!! as Boolean)
    }

}