package com.philip.leeswijzer_app.sections

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.philip.leeswijzer_app.courses.CourseRowView
import com.philip.leeswijzer_app.courses.CourseViewHolder
import philip.com.data.models.Course

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class SelectSectionRecyclerViewAdapter(private val context: Context)
    : RecyclerView.Adapter<CourseViewHolder>() {

    private var courses: MutableList<Course> = ArrayList()

    override fun onBindViewHolder(holder: CourseViewHolder?, position: Int) {
        holder!!.setCourse(this.courses[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseViewHolder {
        return CourseViewHolder(CourseRowView(this.context))
    }

    override fun getItemCount(): Int {
        return this.courses.size
    }

    fun add(course: Course) {
        this.courses.add(course)
        notifyItemChanged(this.courses.size)
    }

    fun addAll(courses: ArrayList<Course>) {
        this.courses.addAll(courses)
        notifyDataSetChanged()
    }


}