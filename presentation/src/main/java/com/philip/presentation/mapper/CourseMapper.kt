package org.buffer.android.boilerplate.presentation.mapper

import org.buffer.android.boilerplate.domain.model.Course
import org.buffer.android.boilerplate.presentation.model.CourseView

/**
 * Map a [CourseView] to and from a [Course] instance when data is moving between
 * this layer and the Domain layer
 */
open class CourseMapper constructor(): Mapper<CourseView, Course> {

    /**
     * Map a [Course] instance to a [CourseView] instance
     */
    override fun mapToView(type: Course): CourseView {
        return CourseView(type.name, type.fieldOfStudy)
    }


}