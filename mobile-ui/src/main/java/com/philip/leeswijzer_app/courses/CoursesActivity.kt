package com.philip.leeswijzer_app.courses


import android.os.Bundle
import com.philip.leeswijzer_app.BaseActivity
import com.philip.leeswijzer_app.R
import com.philip.presentation.course.SelectCourseViewModel
import org.buffer.android.boilerplate.presentation.browse.SelectCourseViewModelFactory

/**
 * Activity for presenting Course related data.
 *
 * @author Philip Wong
 * @since 24-11-17
 **/
class CoursesActivity : BaseActivity() {

    lateinit var viewModelFactory: SelectCourseViewModelFactory
    private lateinit var selectCourseViewModel: SelectCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectCourseFragment = SelectCourseFragment()

        this.fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, selectCourseFragment, SelectCourseFragment.TAG)
                .commit()
    }

}
