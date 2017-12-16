package com.philip.leeswijzer_app.courses

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import com.philip.leeswijzer_app.R

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class CourseViewHolder(view: CourseRowView) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var nameTextView: TextView

    init {
        nameTextView = view.findViewById(R.id.course_name_text_view)
    }

    override fun onClick(p0: View?) {
        Log.d("Application", "CourseViewHolder: onClick: ")
    }

}