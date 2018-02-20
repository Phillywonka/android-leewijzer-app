package com.philip.leeswijzer_app.sections

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.philip.presentation.model.SectionView

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class SelectSectionRecyclerViewAdapter(private val context: Context)
    : RecyclerView.Adapter<SectionViewHolder>() {

    private var sections: MutableList<SectionView> = ArrayList()

    private var onItemClickListener: OnSectionViewItemClickListener? = null

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = sections[position]
        holder.setValues(section)

        val sectionRowView = holder.itemView as SectionRowView
        if (this.onItemClickListener != null) {
            sectionRowView.checkBox.setOnClickListener({
                this.onItemClickListener!!.onClick(section)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SectionViewHolder {
        return SectionViewHolder(SectionRowView(this.context))
    }

    override fun getItemCount(): Int {
        return this.sections.size
    }

    fun clear() {
        this.sections.clear()
        this.notifyDataSetChanged()
    }

    fun add(section: SectionView) {
        this.sections.add(section)
        notifyItemChanged(this.sections.size)
    }

    fun addAll(sections: List<SectionView>) {
        this.sections.addAll(sections)
        notifyDataSetChanged()
    }

    fun setItemSelected(sectionId: Int) {
        val sectionView = this.sections.find { it.id == sectionId }!!
        sectionView.isChecked = !sectionView.isChecked
    }

    fun setOnItemClickListener(onItemClickListener: OnSectionViewItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnSectionViewItemClickListener {
        fun onClick(sectionView: SectionView)
    }
}
