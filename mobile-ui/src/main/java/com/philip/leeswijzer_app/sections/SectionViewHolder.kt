package com.philip.leeswijzer_app.sections

import android.support.v7.widget.RecyclerView
import android.view.View
import com.philip.presentation.model.SectionView

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class SectionViewHolder(private val sectionRowView: SectionRowView) : RecyclerView.ViewHolder(sectionRowView), View.OnClickListener {

    lateinit var section: SectionView

    init {
        sectionRowView.setOnClickListener(this)
    }

    fun setValues(sectionView: SectionView) {
        this.section = sectionView
        sectionRowView.nameTextView.text = sectionView.name
        sectionRowView.checkBox.isChecked = sectionView.isChecked
    }

    override fun onClick(p0: View?) {
        this.section.isChecked = !this.section.isChecked
        sectionRowView.checkBox.isChecked = !this.section.isChecked
    }
}