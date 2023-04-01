package com.example.googlebooks.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlebooks.bookadapter.IAdapterHandler
import com.example.googlebooks.bookadapter.SearchBooksAdapter
import com.example.googlebooks.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), ISearchView {

	private lateinit var binding: FragmentSearchBinding
	private lateinit var searchPresenter: ISearchPresenter
	private lateinit var adapterHandler: IAdapterHandler

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?): View {

		binding = FragmentSearchBinding.inflate(layoutInflater)

		SearchPresenter(this).let {
			searchPresenter = it
			adapterHandler = it
		}

		searchPresenter.onViewCreated()

		setOnClickListeners()
		initRecyclerView()

		return binding.root
	}

	override fun showEmptyQueryMess() {
		Toast.makeText(this.context, "Enter the search query", Toast.LENGTH_LONG).show()
	}


	private fun setOnClickListeners() {
		binding.searchButton.setOnClickListener {
			searchPresenter.onSearchButtonPressed(query = getSearchQuery())
		}
	}

	private fun getSearchQuery(): String = binding.editText.text.toString()

	private fun initRecyclerView() {
		binding.rvBooks.apply {
			adapter = SearchBooksAdapter(adapterHandler)
			layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
			addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
		}
	}

	@SuppressLint("NotifyDataSetChanged")
	override fun reloadBookList() {
		binding.rvBooks.adapter?.notifyDataSetChanged()
	}

	override fun showErrorMess(error: String) {
		Toast.makeText(this.context, error, Toast.LENGTH_LONG).show()
	}
}