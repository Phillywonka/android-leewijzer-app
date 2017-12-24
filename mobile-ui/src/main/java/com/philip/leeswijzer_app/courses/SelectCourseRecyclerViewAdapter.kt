package com.philip.leeswijzer_app.courses

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.philip.presentation.model.CourseView

/**
 * @author Philip Wong
 * @since 30-11-17
 **/
class SelectCourseRecyclerViewAdapter(private val context: Context)
    : RecyclerView.Adapter<CourseViewHolder>() {

    private var courses: MutableList<CourseView> = ArrayList()

    private lateinit var onItemClickListener: OnCourseViewItemClickListener

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.nameTextView.text = course.name
        holder.itemView.setOnClickListener({ this.onItemClickListener.onClick(course) })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseViewHolder {
        return CourseViewHolder(CourseRowView(this.context))
    }

    override fun getItemCount(): Int {
        return this.courses.size
    }

    fun add(course: CourseView) {
        this.courses.add(course)
        this.notifyItemChanged(this.courses.size)
    }

    fun addAll(courseViews: List<CourseView>?) {
        this.courses.addAll(courseViews!!)
        this.notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnCourseViewItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnCourseViewItemClickListener {
        fun onClick(courseView: CourseView)
    }
}