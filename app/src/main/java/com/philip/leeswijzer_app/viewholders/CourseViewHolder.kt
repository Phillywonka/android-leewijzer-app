package com.philip.leeswijzer_app.viewholders

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.philip.leeswijzer_app.models.Course
import com.philip.leeswijzer_app.rows.SectionRowView
import kotlinx.android.synthetic.main.row_course.view.*

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class CourseViewHolder(view: SectionRowView) : RecyclerView.ViewHolder(view), View.OnClickListener {

    fun setCourse(course: Course) {
        itemView.course_name_text_view.text = course.name
//        itemView.field_of_study.text = course.fieldOfStudy
    }

    override fun onClick(p0: View?) {
        Log.d("Application", "CourseViewHolder: onClick: ")
    }

}