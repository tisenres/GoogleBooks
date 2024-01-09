package com.example.googlebooks.app.features.search

import android.graphics.Bitmap
import com.example.googlebooks.data.remote.Remote
import com.example.googlebooks.data.repository.MemoryRepository
import com.example.googlebooks.app.features.search.entity.Book
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchModel(private var outputPort: ModelOutputPort) : ISearchModel {
	private var books: MutableList<Book> = mutableListOf()
	private val repo = MemoryRepository
	private val images: MutableMap<String, Pair<Bitmap?, Disposable?>> = mutableMapOf()
	private val modelScope = CoroutineScope(Dispatchers.IO)

	override fun getBooks(query: String) {

		val handler = CoroutineExceptionHandler { _, exception ->
			exception.printStackTrace()
			outputPort.onFetchError(exception.message ?: "Error")
		}

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
		return images[url]?.first ?: downloadImage(url)
	}

	override fun downloadImage(url: String): Bitmap? {
		var bitmap: Bitmap? = null
//		val disposable = Remote.fetchImage(url)
//			.observeOn(AndroidSchedulers.mainThread())
//			.subscribe(
//				{ response ->
//					bitmap = BitmapFactory.decodeStream(response.byteStream())
//				},
//				{ ex ->
//					ex.printStackTrace()
//				}
//			)
//		images[url] = Pair(bitmap, disposable)

		return bitmap
	}

}
