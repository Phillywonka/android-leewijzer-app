package org.buffer.android.boilerplate.domain.model

/**
 * Representation for a [Course] fetched from an external layer data source
 */
data class Course(val name: String,
                  val fieldOfStudy: String,
                  val sections: List<Section>)

