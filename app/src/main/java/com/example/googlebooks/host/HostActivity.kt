package com.example.googlebooks.host

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googlebooks.R
import com.example.googlebooks.databinding.ActivityHostBinding
import com.example.googlebooks.search.ViewPagerAdapter

class HostActivity : AppCompatActivity(), IHostView {

	private lateinit var binding: ActivityHostBinding
	private lateinit var hostPresenter: IHostPresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityHostBinding.inflate(layoutInflater)
		setContentView(binding.root)

		hostPresenter = HostPresenter(this)

		initViewPager()
		initBottomNavigation()

	}

	private fun initBottomNavigation() {
		binding.bottomNavigation.setOnItemSelectedListener {
			when (it.itemId) {
				R.id.search -> binding.viewPager.currentItem = 0
				else -> binding.viewPager.currentItem = 1
			}
			true
		}
	}

	private fun initViewPager() {
		binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, this.lifecycle)
	}
}