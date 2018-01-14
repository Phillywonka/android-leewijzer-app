package com.philip.presentation.mapper

import com.philip.presentation.model.SectionView
import org.buffer.android.boilerplate.presentation.mapper.Mapper
import philip.com.domain.model.Section

/**
 * Map a [SectionView] to and from a [Section] instance when data is moving between
 * this layer and the Domain layer
 */
open class SectionMapper : Mapper<SectionView, Section> {

    /**
     * Map a [Section] instance to a [SectionView] instance
     */
    override fun mapToView(type: Section): SectionView {
        return SectionView(type.id, type.name, false, type.courseName)
    }


}