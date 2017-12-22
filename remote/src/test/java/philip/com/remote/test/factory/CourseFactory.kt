package philip.com.remote.test.factory

import philip.com.remote.CourseService
import philip.com.remote.model.CourseModel
import philip.com.remote.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for Course related instances
 */
class CourseFactory {

    companion object Factory {

        fun makeCourseResponse(): CourseService.CourseResponse {
            val courseResponse = CourseService.CourseResponse()
            courseResponse.courses = makeCourseModelList(5)
            return courseResponse
        }

        fun makeCourseModelList(count: Int): List<CourseModel> {
            val courseEntities = mutableListOf<CourseModel>()
            repeat(count) {
                courseEntities.add(makeCourseModel())
            }
            return courseEntities
        }

        fun makeCourseModel(): CourseModel {
            return CourseModel(randomUuid(), randomUuid())
        }

    }

}