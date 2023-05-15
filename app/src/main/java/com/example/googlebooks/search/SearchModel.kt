package com.example.googlebooks.search

import android.graphics.Bitmap
import com.example.googlebooks.remote.Remote
import com.example.googlebooks.repository.MemoryRepository
import com.example.googlebooks.search.entity.Book
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody


class SearchModel(private var outputPort: ModelOutputPort) : ISearchModel {
	private var books: MutableList<Book> = mutableListOf()
	private var booksDisposable: Disposable? = null

	private val repo = MemoryRepository

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

	override fun getImage(url: String): Single<ResponseBody> {
		return Remote.fetchImage(url)
			.observeOn(AndroidSchedulers.mainThread())
	}

}
