package com.example.googlebooks.search

import com.example.googlebooks.bookadapter.IAdapterHandler
import com.example.googlebooks.search.entity.Book

class SearchPresenter(private var searchView: ISearchView) : ISearchPresenter, ModelOutputPort,
	IAdapterHandler {

	private val searchModel: ISearchModel = SearchModel(this)

	override fun onSearchButtonPressed(query: String) {
		if (query.isNotBlank()) {
			searchModel.getBooks(query = query)
		} else {
			searchView.showEmptyQueryMess()
		}
	}

	override fun onFavoritesButtonPressed(book: Book) {
		searchModel.toggleFavoriteStatus(book)
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
