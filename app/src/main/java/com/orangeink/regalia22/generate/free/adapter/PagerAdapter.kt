package com.orangeink.regalia22.generate.free.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.orangeink.regalia22.generate.free.ui.fragment.AlumniFragment
import com.orangeink.regalia22.generate.free.ui.fragment.StudentFragment

class PagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StudentFragment()
            1 -> AlumniFragment()
            else -> Fragment()
        }
    }
}