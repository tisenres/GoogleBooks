package com.example.testprojectgooglebooks.search

import com.example.testprojectgooglebooks.search.entity.Book

class SearchPresenter(private var searchView: ISearchView) : ISearchPresenter, ModelOutputPort {

	private val searchModel: ISearchModel = SearchModel(this)

	override fun onSearchButtonPressed(query: String) {
		if (query.isNotBlank()) {
			searchModel.getBooks(query = query)
		} else {
			searchView.showEmptyQueryMess()
		}
	}

	override fun getBook(position: Int): Book = searchModel.getBook(position)

	override fun getBooksCount(): Int = searchModel.getBooksCount()

	override fun onBookReceived() {
		searchView.reloadBookList()
	}

	override fun onFetchError(message: String) {
		searchView.showErrorMess(message)
	}


}
