package com.orangeink.regalia22.generate.free.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.orangeink.regalia22.databinding.ActivityFreeEntryBinding
import com.orangeink.regalia22.generate.free.adapter.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FreeEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFreeEntryBinding

    private val tabArray = arrayOf("Student", "Alumni")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFreeEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTabView()
    }

    private fun initTabView() {
        val adapter = PagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabArray[position]
        }.attach()
    }

}