package com.philip.leeswijzer_app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.philip.leeswijzer_app.courses.CoursesActivity
import com.philip.leeswijzer_app.statistics.StatisticsActivity

/**
 * Abstract Activity class that contains the configuration methods for the bottom navigation bar.
 *
 * @see Activity
 * @see BottomNavigationView
 *
 * @author Philip Wong
 * @since 24-11-2017
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        this.setupNavigationMenu()
    }

    private fun setupNavigationMenu() {

        val bottomViewNavigationView = findViewById<BottomNavigationView>(R.id.navigation)
        bottomViewNavigationView.setOnNavigationItemSelectedListener { view ->

            when (view.itemId) {
                R.id.action_courses -> {
                    this.startActivity(this, CoursesActivity::class.java)
                    true
                }
                R.id.action_statistics -> {
                    this.startActivity(this, StatisticsActivity::class.java)
                    true
                }
                else -> false
            }

        }
    }

    /**
     * Start a new activity and hide de draw animations.
     */
    private fun startActivity(context: Context, activityToStart: Class<out Any>) {

        // Return if the current class is the activity to start.
        if (this.javaClass == activityToStart)
        {
            return
        }

        val intent = Intent(context, activityToStart)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        this.startActivity(intent)
    }
}
