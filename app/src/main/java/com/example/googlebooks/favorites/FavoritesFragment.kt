package com.example.googlebooks.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlebooks.bookadapter.BookListAdapter
import com.example.googlebooks.bookadapter.IAdapterHandler
import com.example.googlebooks.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment(), IFavoritesView {

	private lateinit var binding: FragmentFavoritesBinding
	private lateinit var favoritesPresenter: IFavoritesPresenter
	private lateinit var adapterHandler: IAdapterHandler


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?): View {
		
		binding = FragmentFavoritesBinding.inflate(layoutInflater)
		FavoritesPresenter(this).let {
			favoritesPresenter = it
			adapterHandler = it
		}

		initRecyclerView()

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		favoritesPresenter.onViewCreated()
	}

	override fun onDestroy() {
		super.onDestroy()
		favoritesPresenter.onViewDestroy()
	}

	private fun initRecyclerView() {
		binding.rvBooks.apply {
			adapter = BookListAdapter(adapterHandler)
			layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
			addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
		}
	}
	@SuppressLint("NotifyDataSetChanged")
	override fun reloadBookList() {
		binding.rvBooks.adapter?.notifyDataSetChanged()
	}
}