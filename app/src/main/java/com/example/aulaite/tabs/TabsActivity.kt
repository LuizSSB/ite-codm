package com.example.aulaite.tabs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.aulaite.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)

        val viewPager = findViewById<ViewPager2>(R.id.pager)
        viewPager.adapter = TabsAdapter(this)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val mediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when(TabsAdapter.Tabs.values()[position]) {
                TabsAdapter.Tabs.TEXT -> "Texto"
                TabsAdapter.Tabs.LOGIN -> "Login"
                TabsAdapter.Tabs.SCROLL -> "Scroll"
                TabsAdapter.Tabs.SETTINGS -> "Ajustes"
            }
        }
        mediator.attach()
    }
}
