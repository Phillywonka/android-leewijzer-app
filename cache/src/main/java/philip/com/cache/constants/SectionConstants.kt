package philip.com.cache.constants

/**
 * Defines constants for the Section Table
 */
object SectionConstants {

    const val TABLE_NAME = "sections"

    const val QUERY_SECTIONS = "SELECT * FROM $TABLE_NAME WHERE courseName=:courseName"

    const val DELETE_ALL_SECTIONS = "DELETE FROM" + " " + TABLE_NAME

}