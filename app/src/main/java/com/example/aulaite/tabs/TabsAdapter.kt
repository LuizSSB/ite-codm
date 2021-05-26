package com.example.aulaite.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aulaite.fragment.TextFragment
import com.example.aulaite.tabs.fragments.ScrollingFragment
import com.example.aulaite.tabs.fragments.SettingsFragment
import com.example.aulaite.tabs.fragments.ui.login.LoginFragment

class TabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    enum class Tabs {
        TEXT,
        LOGIN,
        SCROLL,
        SETTINGS
    }

    override fun getItemCount(): Int {
        return Tabs.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return when(Tabs.values()[position]) {
            Tabs.TEXT ->  TextFragment.newInstance("Fragment na página $position")
            Tabs.LOGIN -> LoginFragment()
            Tabs.SCROLL -> ScrollingFragment()
            Tabs.SETTINGS -> SettingsFragment()
        }
    }
}
