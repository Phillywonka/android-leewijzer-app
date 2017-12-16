package com.philip.leeswijzer_app.sections

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.philip.leeswijzer_app.R

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class SectionViewHolder(view: SectionRowView) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var nameTextView: TextView
    var checkBox: CheckBox

    init {
        nameTextView = view.findViewById(R.id.section_name_text_view)
        checkBox = view.findViewById(R.id.section_checkbox)
    }

    override fun onClick(p0: View?) {
        Log.d("Application", "SectionViewHolder: onClick: ")
    }

}