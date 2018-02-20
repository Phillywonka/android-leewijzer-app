package com.philip.leeswijzer_app.sections

import android.support.v7.widget.RecyclerView
import com.philip.presentation.model.SectionView

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class SectionViewHolder(private val sectionRowView: SectionRowView) : RecyclerView.ViewHolder(sectionRowView) {

    lateinit var section: SectionView

    fun setValues(sectionView: SectionView) {
        this.section = sectionView
        sectionRowView.nameTextView.text = sectionView.name
        sectionRowView.checkBox.isChecked = sectionView.isChecked
    }

}