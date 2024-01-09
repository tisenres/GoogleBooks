package com.example.googlebooks.app.features.search

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
import com.example.googlebooks.app.features.bookadapter.BookListAdapter
import com.example.googlebooks.app.features.bookadapter.IAdapterHandler
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

		setOnClickListeners()
		initRecyclerView()

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		searchPresenter.onViewCreated()
	}

	override fun showEmptyQueryMess() {
		Toast.makeText(this.context, "Enter the search query", Toast.LENGTH_LONG).show()
	}


	private fun setOnClickListeners() {
		binding.searchView.setOnClickListener {
			searchPresenter.onSearchButtonPressed(query = getSearchQuery())
		}
	}

	override fun clearBookList() {
		(binding.rvBooks.adapter as BookListAdapter).clearItems()
	}

	override fun startProgressBar() {
		binding.progressBar.visibility = View.VISIBLE
	}

	override fun stopProgressBar() {
		binding.progressBar.visibility = View.GONE
	}

	private fun getSearchQuery(): String = binding.searchView.query.toString()

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

	override fun showErrorMess(error: String) {
		Toast.makeText(this.context, error, Toast.LENGTH_LONG).show()
	}
}