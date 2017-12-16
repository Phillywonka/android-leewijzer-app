package org.buffer.android.boilerplate.domain.model

/**
 * Representation for a [Student] fetched from an external layer data source
 */
data class Student(val name: String,
                   val fieldOfStudy: String,
                   val sections: List<Section>)

