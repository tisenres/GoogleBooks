package com.example.googlebooks.app.features.search

import com.example.googlebooks.app.features.bookadapter.IAdapterHandler
import com.example.googlebooks.app.features.search.entity.Book
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchPresenter(private var searchView: ISearchView) : ISearchPresenter, ModelOutputPort,
    IAdapterHandler {

    private val searchModel: ISearchModel = SearchModel()
    private var disposable: Disposable? = null
    private val handler = CoroutineExceptionHandler { _, exception ->
		exception.printStackTrace()
	}
    private val presenterScope = CoroutineScope(Dispatchers.Main + handler)

    override fun onSearchButtonPressed(query: String) {
        searchModel.clearDataSet()
        searchView.clearBookList()

        if (query.isNotBlank()) {
            searchView.startProgressBar()
            searchModel.fetchBooks(query = query)
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

    override fun onBooksReceived() {
        searchView.reloadBookList()
        searchView.stopProgressBar()
    }

    override fun onViewCreated() {
        presenterScope.launch {
            searchModel.getRepositoryChangeFlow()
                .collect {
                    searchView.reloadBookList()
                }
        }
        presenterScope.launch {
            searchModel.getUpdateBooksFlow()
                .collect {
                    searchView.reloadBookList()
                }
        }
    }

    override fun onViewDestroy() {
        disposable?.dispose()
    }

    override fun onFetchError(message: String) {
        searchView.showErrorMessage(message)
    }

    override fun onImageReceived() {
        searchView.reloadBookList()
    }
}