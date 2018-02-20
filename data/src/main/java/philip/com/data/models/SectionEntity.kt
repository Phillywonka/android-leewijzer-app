package philip.com.data.models

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
 data class SectionEntity(
        val id: Int,
        val name: String,
        var isChecked: Boolean,
        val courseName: String)

