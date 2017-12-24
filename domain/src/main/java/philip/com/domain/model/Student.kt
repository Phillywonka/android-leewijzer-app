package philip.com.domain.model

/**
 * Representation for a [Student] fetched from an external layer data source
 */
data class Student(val number: String,
                   val firstName: String,
                   val lastName: String,
                   val password: String)

