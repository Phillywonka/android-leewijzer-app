package com.philip.presentation.model

/**
 * Representation for a [SectionView] instance for this layers Model representation
 */
class SectionView(
        val id: Long,
        val name: String,
        var isChecked: Boolean,
        val courseName: String
)