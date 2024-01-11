package com.example.googlebooks.app.features.search

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.googlebooks.data.remote.Remote
import com.example.googlebooks.data.repository.MemoryRepository
import com.example.googlebooks.app.features.search.entity.Book
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchModel(private var outputPort: ModelOutputPort) : ISearchModel {

	private var books: MutableList<Book> = mutableListOf()
	private val repo = MemoryRepository
	private val images: MutableMap<String, Bitmap?> = mutableMapOf()
	private val modelScope = CoroutineScope(Dispatchers.IO)
	private val handler = CoroutineExceptionHandler { _, exception ->
		exception.printStackTrace()
		outputPort.onFetchError(exception.message ?: "Error")
	}

	override fun getBooks(query: String) {
		modelScope.launch(handler) {
			books.clear()
			val newBooks = Remote.fetchBooks(query = query)
			withContext(Dispatchers.Main) {
				books.addAll(newBooks)
				outputPort.onBookReceived()
			}
		}
	}

	override fun isBookFavoriteNow(book: Book): Boolean {
		return repo.contains(book)
	}

	override fun getRepositoryChangeFlow(): SharedFlow<Boolean> {
		return repo.repoChangedFlow
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
	}

	override fun getImage(url: String): Bitmap? {
		var bitmap: Bitmap? = null

		images[url] ?: run {
			modelScope.launch(handler + Dispatchers.Main) {
				bitmap = downloadImage(url).await()
				images[url] = bitmap
			}
		}
		return bitmap
	}

	override suspend fun downloadImage(url: String): Deferred<Bitmap?> {
		return modelScope.async (handler) {
			val response = Remote.fetchImage(url)
			BitmapFactory.decodeStream(response.byteStream())
		}
	}
}
