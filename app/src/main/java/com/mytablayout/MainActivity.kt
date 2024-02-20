package com.mytablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mytablayout.adaptors.DynamicPagerAdapter
import com.mytablayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {

        binding.vpViewPager.adapter = DynamicPagerAdapter(3)

       // binding.tabLayout.setupWithViewPager(binding.vpViewPager)
       // binding.tabLayout.enableScrollableTabs()

    }

}