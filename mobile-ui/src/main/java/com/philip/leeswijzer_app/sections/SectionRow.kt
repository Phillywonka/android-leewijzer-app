package com.philip.leeswijzer_app.sections

import android.content.Context
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import com.philip.leeswijzer_app.R

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class SectionRowView(context: Context?) : RelativeLayout(context) {

    var nameTextView: TextView
    var checkBox: CheckBox

    init {
        LayoutInflater.from(context).inflate(R.layout.row_section, this)
        this.nameTextView = this.findViewById(R.id.section_name_text_view)
        this.checkBox = this.findViewById(R.id.section_checkbox)
    }

}