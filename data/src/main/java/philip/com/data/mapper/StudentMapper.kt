package philip.com.data.mapper

import org.buffer.android.boilerplate.data.mapper.Mapper
import philip.com.data.models.StudentEntity
import philip.com.domain.model.Student


/**
 * Map a [Student] to and from a [StudentEntity] instance when data is moving between
 * this later and the Domain layer
 */
open class StudentMapper : Mapper<StudentEntity, Student> {


    /**
     * Map a [StudentEntity] instance to a [StudentEntity] instance
     */
    override fun mapFromEntity(type: StudentEntity): Student {
        return Student(type.number, type.fistName, type.lastName, type.password)
    }

    /**
     * Map a [StudentEntity] instance to a [StudentEntity] instance
     */
    override fun mapToEntity(type: Student): StudentEntity {

        return StudentEntity(type.number, type.firstName, type.lastName, type.password)
    }


}