package philip.com.domain.model

/**
 * Representation for a [Section] fetched from an external layer data source
 */
data class Section(
        val id: Int,
        val name: String,
        val isChecked: Boolean,
        val courseName: String)

