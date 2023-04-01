package com.example.googlebooks.search

import com.example.googlebooks.bookadapter.IAdapterHandler
import com.example.googlebooks.search.entity.Book
import io.reactivex.disposables.Disposable

class SearchPresenter(private var searchView: ISearchView) : ISearchPresenter, ModelOutputPort,
	IAdapterHandler {

	private val searchModel: ISearchModel = SearchModel(this)
	private var disposable: Disposable? = null

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

	override fun isBookFavoriteNow(book: Book): Boolean {
		return searchModel.isBookFavoriteNow(book)
	}

	override fun getBook(position: Int): Book = searchModel.getBook(position)

	override fun getBooksCount(): Int = searchModel.getBooksCount()

	override fun onBookReceived() {
		searchView.reloadBookList()
	}

	override fun onViewCreated() {
		disposable = searchModel.getRepositoryChangeSubject().subscribe {
			searchView.reloadBookList()
		}
	}

	override fun onViewDestroy() {
		disposable?.dispose()
	}

	override fun onFetchError(message: String) {
		searchView.showErrorMess(message)
	}


}
