package com.philip.leeswijzer_app.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.philip.leeswijzer_app.models.Course
import com.philip.leeswijzer_app.rows.CourseRowView
import com.philip.leeswijzer_app.viewholders.CourseViewHolder

/**
 * @author Philip Wong
 * @since 30-11-17
 **/
class SelectCourseRecyclerViewAdapter(private val context: Context)
    : RecyclerView.Adapter<CourseViewHolder>() {

    private var courses: MutableList<Course> = ArrayList()

    override fun onBindViewHolder(holder: CourseViewHolder?, position: Int) {
        holder!!.setCourseName(this.courses[position].name)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseViewHolder {
        return CourseViewHolder(CourseRowView(this.context))
    }

    override fun getItemCount(): Int {
        return this.courses.size
    }

    fun add(course: Course) {
        this.courses.add(0, course)
        notifyItemInserted(0)
    }

    fun addAll(courses: ArrayList<Course>) {
        this.courses.addAll(courses)
        notifyDataSetChanged()
    }


}