package com.example.testprojectgooglebooks.search

import com.example.testprojectgooglebooks.remote.Remote
import com.example.testprojectgooglebooks.search.entity.Book
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class SearchModel(private var outputPort: ModelOutputPort) : ISearchModel {
	private var books: MutableList<Book> = mutableListOf()
	private var disposable: Disposable? = null

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

	override fun getBooksCount(): Int = books.size

	override fun getBook(position: Int) = books[position]

}
