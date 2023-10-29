package com.example.googlebooks.search

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.googlebooks.data.remote.Remote
import com.example.googlebooks.data.repository.MemoryRepository
import com.example.googlebooks.search.entity.Book
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable


class SearchModel(private var outputPort: ModelOutputPort) : ISearchModel {
	private var books: MutableList<Book> = mutableListOf()
	private var booksDisposable: Disposable? = null
	private val repo = MemoryRepository
	private val images: MutableMap<String, Pair<Bitmap?, Disposable?>> = mutableMapOf()


	override fun getBooks(query: String) {
		booksDisposable = Remote.fetchBooks(query = query)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ newBooks ->
						   books.clear()
						   books.addAll(newBooks)
						   outputPort.onBookReceived()
					   },
					   {
						   it.printStackTrace()
						   outputPort.onFetchError(it.message ?: "Error")
					   })
	}

	override fun isBookFavoriteNow(book: Book): Boolean {
		return repo.contains(book)
	}

	override fun getRepositoryChangeSubject(): Observable<Boolean> {
		return repo.behaviorSubject
			.observeOn(AndroidSchedulers.mainThread())
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
		val disposable = Remote.fetchImage(url)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{ response ->
					bitmap = BitmapFactory.decodeStream(response.byteStream())
				},
				{ ex ->
					ex.printStackTrace()
				}
			)
		images[url] = Pair(bitmap, disposable)

		return bitmap
	}

}
