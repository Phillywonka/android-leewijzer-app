package com.philip.leeswijzer_app.courses

import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.philip.leeswijzer_app.R

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class CourseViewHolder(view: CourseRowView) : RecyclerView.ViewHolder(view){

    var nameTextView: TextView

    init {
        nameTextView = view.findViewById(R.id.course_name_text_view)
    }

}