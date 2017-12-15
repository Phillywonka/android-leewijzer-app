package com.philip.leeswijzer_app.courses

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import philip.com.data.models.Course

/**
 * @author Philip Wong
 * @since 30-11-17
 **/
class SelectCourseRecyclerViewAdapter(private val context: Context)
    : RecyclerView.Adapter<CourseViewHolder>() {

    private var courses: MutableList<Course> = ArrayList()

    private lateinit var onItemClickListener : View.OnClickListener

    override fun onBindViewHolder(holder: CourseViewHolder?, position: Int) {
        Log.d("Application", "SelectCourseRecyclerViewAdapter: onBindViewHolder: ")
        holder!!.itemView.setOnClickListener({this.onItemClickListener.onClick(holder.itemView)})
        holder.setCourse(this.courses[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseViewHolder {
        return CourseViewHolder(CourseRowView(this.context))
    }

    override fun getItemCount(): Int {
        return this.courses.size
    }

    fun add(course: Course) {
        this.courses.add(course)
        this.notifyItemChanged(this.courses.size)
    }

    fun addAll(courses: ArrayList<Course>) {
        this.courses.addAll(courses)
        this.notifyDataSetChanged()
    }

    fun setOnItemClickLister(onItemClickListener: View.OnClickListener) {
        Log.d("Application", "SelectCourseRecyclerViewAdapter: setOnItemClickLister: ")
        this.onItemClickListener = onItemClickListener
    }


}