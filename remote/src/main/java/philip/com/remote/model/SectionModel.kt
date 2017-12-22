package philip.com.remote.model

/**
 * Representation for a [SectionModel] fetched from the API
 */
class SectionModel(
        val id: Long,
        val name: String,
        val isSelected: Boolean,
        val courseName: String
)