package com.example.testprojectgooglebooks.host

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.testprojectgooglebooks.R
import com.example.testprojectgooglebooks.databinding.ActivityHostBinding
import com.example.testprojectgooglebooks.search.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class HostActivity : AppCompatActivity(), IHostView {

	private lateinit var binding: ActivityHostBinding
	private lateinit var hostPresenter: IHostPresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityHostBinding.inflate(layoutInflater)
		setContentView(binding.root)

		hostPresenter = HostPresenter(this)

//		setOnClickListeners()

		initViewPager()
		initBottomNavigation()

	}

	private fun initBottomNavigation() {
//		binding.bottomNavigation.setOnItemSelectedListener {
//			when (it.itemId) {
//				R.id.search -> binding.viewPager.currentItem = 0
//				else -> binding.viewPager.currentItem = 1
//			}
//			true
//		}
	}

	private fun initViewPager() {
		binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, this.lifecycle)
	}

//	private fun setOnClickListeners() {
//		binding.searchFragmentButton.setOnClickListener {
//			hostPresenter.onPressedSearchButton()
//		}
//		binding.favoritesFragmentButton.setOnClickListener {
//			hostPresenter.onPressedFavoriteButton()
//		}
//	}

//	override fun showSearchFragment() {
//		val fr = SearchFragment()
//
//		val transaction = supportFragmentManager.beginTransaction()
//		transaction.replace(binding.fragmentContainerView.id, fr)
//		transaction.commit()
//	}
//
//	override fun showFavoriteFragment() {
//		val fr = FavoritesFragment()
//
//		val transaction = supportFragmentManager.beginTransaction()
//		transaction.replace(binding.fragmentContainerView.id, fr)
//		transaction.commit()
//	}
}