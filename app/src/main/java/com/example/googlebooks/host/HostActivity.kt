package com.example.googlebooks.host

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.googlebooks.databinding.ActivityHostBinding
import com.google.android.material.tabs.TabLayoutMediator

class HostActivity : AppCompatActivity(), IHostView {

	private lateinit var binding: ActivityHostBinding
	private lateinit var hostPresenter: IHostPresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityHostBinding.inflate(layoutInflater)
		setContentView(binding.root)

		hostPresenter = HostPresenter(this)

		initViewPager()
		initTabNavigation()

	}

	private fun initTabNavigation() {
		TabLayoutMediator(binding.tabNavigation, binding.viewPager) { tab, position ->
			when (position) {
				0 -> tab.text = "search"
				1 -> tab.text = "favorites"
			}
		}.attach()
	}

	private fun initViewPager() {
		binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, this.lifecycle)
	}
}