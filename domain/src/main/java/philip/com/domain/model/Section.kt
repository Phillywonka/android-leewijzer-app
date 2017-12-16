package org.buffer.android.boilerplate.domain.model

/**
 * Representation for a [Section] fetched from an external layer data source
 */
data class Section(
        val id: Long,
        val name: String,
        val isChecked: Boolean)

