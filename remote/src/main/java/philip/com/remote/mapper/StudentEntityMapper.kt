package philip.com.remote.mapper

import philip.com.data.models.StudentEntity
import philip.com.remote.model.StudentModel

/**
 * @author Philip Wong
 * @since 09-03-18
 * Map a [StudentModel] to and from a [StudentEntity] instance when data is moving between
 * this later and the Data layer
 */
open class StudentEntityMapper : EntityMapper<StudentModel, StudentEntity> {

    /**
     * Map an instance of a [StudentModel] to a [StudentEntity] model
     */
    override fun mapFromRemote(type: StudentModel): StudentEntity {
        return StudentEntity(type.number, type.firstName, type.lastName, type.password)
    }

}
