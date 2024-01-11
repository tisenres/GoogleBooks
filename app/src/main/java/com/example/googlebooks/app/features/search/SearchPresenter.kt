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

        if (query.isNotBlank()) {
            searchModel.clearDataSet()
            searchModel.fetchBooks(query = query)
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

        presenterScope.launch {
            searchModel.getProgressBarStatus()
                .collect { state ->
                    when (state) {
                        ProgressBarStatus.WORK -> searchView.startProgressBar()
                        ProgressBarStatus.IDLE -> searchView.stopProgressBar()
                    }
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