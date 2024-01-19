package com.example.googlebooks.app.features.search

import android.graphics.BitmapFactory
import com.example.googlebooks.data.remote.Remote
import com.example.googlebooks.data.repository.MemoryRepository
import com.example.googlebooks.app.features.search.entity.Book
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchModel: ISearchModel {

	private var books: MutableList<Book> = mutableListOf()
	private val repo = MemoryRepository
	private val _updateBooksFlow = MutableSharedFlow<Boolean>()
	private val _progressBarState = MutableStateFlow(ProgressBarStatus.IDLE)
	private val handler = CoroutineExceptionHandler { _, exception ->
		exception.printStackTrace()
	}
	private val modelScope = CoroutineScope(handler + Dispatchers.IO)

	override fun fetchBooks(query: String) {
		modelScope.launch {
			_progressBarState.emit(ProgressBarStatus.WORK)
			books.clear()
			val newBooks = Remote.fetchBooks(query = query)
			books.addAll(newBooks)
			_updateBooksFlow.emit(true)
			_progressBarState.emit(ProgressBarStatus.IDLE)
			fetchImageForEachBook()
		}
	}

	override fun isBookFavoriteNow(book: Book): Boolean {
		return repo.contains(book)
	}

	override fun getRepositoryChangeFlow(): SharedFlow<Boolean> {
		return repo.repoChangedFlow
	}

	override fun getProgressBarStatus(): StateFlow<ProgressBarStatus> {
		return _progressBarState.asStateFlow()
	}

	override fun getUpdateBooksFlow(): SharedFlow<Boolean> {
		return _updateBooksFlow.asSharedFlow()
	}

	override fun toggleFavoriteStatus(book: Book) {
		if (repo.contains(book)) {
			repo.delete(book)
		} else {
			repo.save(book)
		}
	}

	override fun getBooksCount(): Int = books.size

	override fun getBook(position: Int) = books[position]

	override fun clearDataSet() {
		books.clear()
		_updateBooksFlow.tryEmit(true)
	}

	override suspend fun fetchImageForEachBook() {
		books.map { book ->
			book.imageLink?.let { url ->
				modelScope.launch {
					val response = Remote.fetchImage(url)
					val bitmap = BitmapFactory.decodeStream(response.byteStream())
					book.imageBitmap = bitmap
					_updateBooksFlow.emit(true)
				}
			}
		}
	}
}
