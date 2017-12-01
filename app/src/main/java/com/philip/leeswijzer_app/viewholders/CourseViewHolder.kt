package com.philip.leeswijzer_app.viewholders

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.philip.leeswijzer_app.rows.CourseRowView
import kotlinx.android.synthetic.main.row_course.view.*

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class CourseViewHolder(view: CourseRowView) : RecyclerView.ViewHolder(view), View.OnClickListener {

    fun setCourseName(courseName: String) {
        itemView.course_name_text_view.text = courseName
    }

    override fun onClick(p0: View?) {
        Log.d("Application", "CourseViewHolder: onClick: ")
    }

}