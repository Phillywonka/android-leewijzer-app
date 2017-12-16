package com.philip.leeswijzer_app.sections

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import philip.com.data.models.SectionEntity

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class SelectSectionRecyclerViewAdapter(private val context: Context)
    : RecyclerView.Adapter<SectionViewHolder>() {

    private var sections: MutableList<SectionEntity> = ArrayList()

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = sections[position]
        holder.nameTextView.text = section.name
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SectionViewHolder {
        return SectionViewHolder(SectionRowView(this.context))
    }

    override fun getItemCount(): Int {
        return this.sections.size
    }

    fun add(section: SectionEntity) {
        this.sections.add(section)
        notifyItemChanged(this.sections.size)
    }

    fun addAll(sections: ArrayList<SectionEntity>) {
        this.sections.addAll(sections)
        notifyDataSetChanged()
    }


}
