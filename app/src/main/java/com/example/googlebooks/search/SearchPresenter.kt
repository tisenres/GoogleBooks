package com.example.googlebooks.search

import android.util.Log
import com.example.googlebooks.bookadapter.IAdapterHandler
import com.example.googlebooks.search.entity.Book
import io.reactivex.disposables.Disposable

class SearchPresenter(private var searchView: ISearchView) : ISearchPresenter, ModelOutputPort,
	IAdapterHandler {

	private val searchModel: ISearchModel = SearchModel(this)
	private var disposable: Disposable? = null

	override fun onSearchButtonPressed(query: String) {

		searchModel.clearDataSet()
		searchView.clearBookList()

		if (query.isNotBlank()) {
			searchModel.getBooks(query = query)
			searchView.startProgressBar()
		} else {
			searchView.showEmptyQueryMess()
			searchView.stopProgressBar()
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
		searchView.stopProgressBar()
	}

	override fun onViewCreated() {
		disposable = searchModel.getRepositoryChangeSubject()
			.subscribe({ searchView.reloadBookList() },
						{ it.message?.let { error -> Log.e("My log", error) } })
	}

	override fun onViewDestroy() {
		disposable?.dispose()
	}

	override fun onFetchError(message: String) {
		searchView.showErrorMess(message)
	}


}
