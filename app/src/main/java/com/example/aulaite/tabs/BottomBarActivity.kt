package com.example.aulaite.tabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.aulaite.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception

class BottomBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_bar)

        val viewpager = findViewById<ViewPager2>(R.id.pager)
        viewpager.adapter = TabsAdapter(this)

        val bottomBar = findViewById<BottomNavigationView>(R.id.tabbar)
        bottomBar.setOnNavigationItemSelectedListener { item ->
            val selectedTab = when(item.itemId) {
                R.id.tab_text -> TabsAdapter.Tabs.TEXT
                R.id.tab_login -> TabsAdapter.Tabs.LOGIN
                R.id.tab_scroll -> TabsAdapter.Tabs.SCROLL
                R.id.tab_settings -> TabsAdapter.Tabs.SETTINGS
                else -> throw Exception()
            }

            viewpager.currentItem = selectedTab.ordinal

            true
        }

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomBar.selectedItemId = when(TabsAdapter.Tabs.values()[position]) {
                    TabsAdapter.Tabs.TEXT -> R.id.tab_text
                    TabsAdapter.Tabs.LOGIN -> R.id.tab_login
                    TabsAdapter.Tabs.SCROLL -> R.id.tab_scroll
                    TabsAdapter.Tabs.SETTINGS -> R.id.tab_settings
                }
            }
        })
    }
}
