package com.example.googlebooks.search

import com.example.googlebooks.remote.Remote
import com.example.googlebooks.repository.MemoryRepository
import com.example.googlebooks.search.entity.Book
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class SearchModel(private var outputPort: ModelOutputPort) : ISearchModel {
	private var books: MutableList<Book> = mutableListOf()
	private var disposable: Disposable? = null
	private val repo = MemoryRepository

	override fun getBooks(query: String) {
		disposable = Remote.fetchBooks(query = query)
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

	override fun toggleFavoriteStatus(book: Book) {
		if (repo.contains(book)) {
			repo.delete(book)
		} else {
			repo.save(book)
		}
	}

	override fun getBooksCount(): Int = books.size

	override fun getBook(position: Int) = books[position]

}
