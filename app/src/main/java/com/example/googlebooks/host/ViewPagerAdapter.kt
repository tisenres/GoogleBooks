package com.example.googlebooks.host

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.googlebooks.favorites.FavoritesFragment
import com.example.googlebooks.search.SearchFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

	override fun getItemCount(): Int = 2

	override fun createFragment(position: Int): Fragment {
		return when (position) {
			0 -> SearchFragment()
			else -> FavoritesFragment()
		}
	}
}