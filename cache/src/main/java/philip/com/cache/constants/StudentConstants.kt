package philip.com.cache.constants

/**
 * Defines constants for the Course Table
 */
object StudentConstants {

    const val TABLE_NAME = "students"

    const val QUERY_STUDENTS = "SELECT * FROM" + " " + TABLE_NAME

    const val DELETE_ALL_STUDENTS = "DELETE FROM" + " " + TABLE_NAME

}