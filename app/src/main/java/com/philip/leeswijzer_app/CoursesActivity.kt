package com.philip.leeswijzer_app

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.RecyclerView

/**
 * @author Philip Wong
 * @since 24-11-17
 **/
class CoursesActivity : BaseActivity() {

    private lateinit var coursesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        this.setupCoursesRecyclerView()
    }

    private fun setupCoursesRecyclerView() {
//        this.coursesRecyclerView = findViewById(R.id.recycler_view_courses)
    }
}

