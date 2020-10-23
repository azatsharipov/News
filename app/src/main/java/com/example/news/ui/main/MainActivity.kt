package com.example.news.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.news.R
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tl_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        navController.popBackStack()
                        navController.navigate(R.id.homeFragment)
                    }
                    1 -> {
                        navController.popBackStack()
                        navController.navigate(R.id.everythingFragment)
                    }
                    2 -> {
                        navController.popBackStack()
                        navController.navigate(R.id.marksFragment)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
    }
}
