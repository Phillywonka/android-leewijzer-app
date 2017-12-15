package philip.com.data.models

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
data class Course(val name: String,
                  val fieldOfStudy: String,
                  val sections: List<Section>)