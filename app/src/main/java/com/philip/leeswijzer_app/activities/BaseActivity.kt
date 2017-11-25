package com.philip.leeswijzer_app.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.philip.leeswijzer_app.R

/**
 * Abstract Activity class that contains the configuration methods for the bottom navigation
 * bar.
 *
 * @see Activity
 * @see BottomNavigationView
 *
 * @author Philip Wong
 * @since 24-11-2017
 */
abstract class BaseActivity : Activity() {

    private var currentNavItemId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        this.setupNavigationMenu()
    }

    private fun setupNavigationMenu() {
        currentNavItemId = R.id.action_courses

        val bottomViewNavigationView = findViewById<BottomNavigationView>(R.id.navigation)
        bottomViewNavigationView.setOnNavigationItemReselectedListener { view ->

            if (this.currentNavItemId == currentNavItemId) return@setOnNavigationItemReselectedListener

            when (view.itemId) {
                R.id.action_courses -> {
                    this.startActivity(this.applicationContext, CoursesActivity::class.java)
                }
                R.id.action_statistics -> {
                    this.startActivity(this.applicationContext, StatisticsActivity::class.java)
                }
            }
        }
    }

    /**
     * Start a new activity and hide de draw animations.
     */
    private fun startActivity(context: Context, T: Class<out Any>) {
        val intent = Intent(context, T)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        this.startActivity(intent)
    }
}
