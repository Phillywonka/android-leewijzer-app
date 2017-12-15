package com.philip.leeswijzer_app.storage.cache

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import philip.com.cache.constants.StudentConstants
import philip.com.cache.model.CachedStudent


/**
 * @author Philip Wong
 * @since 09-12-17
 **/
@Dao
interface CachedStudentDao {

    @Query(StudentConstants.QUERY_STUDENTS)
    fun loadAllStudents(): List<CachedStudent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: CachedStudent)

    @Query(StudentConstants.DELETE_ALL_STUDENTS)
    fun clearStudents()
}
