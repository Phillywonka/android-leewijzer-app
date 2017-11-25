package com.philip.leeswijzer_app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView

abstract class BaseActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        this.setupNavigationMenu()
    }

    private fun setupNavigationMenu() {
        val bottomViewNavigationView = findViewById<BottomNavigationView>(R.id.navigation)
        bottomViewNavigationView.setOnNavigationItemReselectedListener { view ->

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

    private fun startActivity(context: Context, T: Class<out Any>) {
        val intent = Intent(context, T)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        this.startActivity(intent)
    }
}
