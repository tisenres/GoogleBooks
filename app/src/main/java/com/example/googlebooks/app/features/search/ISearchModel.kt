package com.example.googlebooks.app.features.search

import com.example.googlebooks.app.features.search.entity.Book
import kotlinx.coroutines.flow.SharedFlow

interface ISearchModel {
	fun fetchBooks(query: String)
	fun getBooksCount(): Int
	fun getBook(position: Int): Book
	fun toggleFavoriteStatus(book: Book)
	fun isBookFavoriteNow(book: Book): Boolean
	fun getRepositoryChangeFlow(): SharedFlow<Boolean>
	fun clearDataSet()
	suspend fun fetchImageForEachBook()
	fun getBooksChangedFlow(): SharedFlow<Boolean>
}
