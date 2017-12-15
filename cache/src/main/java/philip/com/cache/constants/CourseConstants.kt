package philip.com.cache.constants

/**
 * Defines constants for the Course Table
 */
object CourseConstants {

    const val TABLE_NAME = "courses"

    const val QUERY_COURSES = "SELECT * FROM" + " " + TABLE_NAME

    const val DELETE_ALL_COURSES = "DELETE FROM" + " " + TABLE_NAME

}