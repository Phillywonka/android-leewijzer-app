package com.philip.leeswijzer_app.sections

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.philip.leeswijzer_app.R

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class SectionRowView(context: Context?) : RelativeLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.row_section, this)
    }
}