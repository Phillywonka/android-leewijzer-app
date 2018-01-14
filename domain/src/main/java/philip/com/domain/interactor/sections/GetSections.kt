package philip.com.domain.interactor.sections

import io.reactivex.Flowable
import philip.com.domain.executor.PostExecutionThread
import philip.com.domain.executor.ThreadExecutor
import philip.com.domain.interactor.FlowableUseCase
import philip.com.domain.model.Course
import philip.com.domain.model.Section
import philip.com.domain.repository.CourseRepository
import philip.com.domain.repository.SectionRepository

/**
 * Use case used for retrieving a [List] of [Course] instances from the [CourseRepository]
 */
open class GetSections(private val sectionRepository: SectionRepository,
                       threadExecutor: ThreadExecutor,
                       postExecutionThread: PostExecutionThread) :
        FlowableUseCase<List<Section>, HashMap<String, String>>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: HashMap<String, String>): Flowable<List<Section>> {
        return sectionRepository.getSections(
                params["student_number"]!!,
                params["course_name"]!!)
    }

}