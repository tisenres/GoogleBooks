package com.example.googlebooks.app.features.search

import android.graphics.Bitmap
import com.example.googlebooks.app.features.search.entity.Book
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.SharedFlow

interface ISearchModel {
	fun getBooks(query: String)
	fun getBooksCount(): Int
	fun getBook(position: Int): Book
	fun toggleFavoriteStatus(book: Book)
	fun isBookFavoriteNow(book: Book): Boolean
	fun getRepositoryChangeFlow(): SharedFlow<Boolean>
	fun clearDataSet()
	suspend fun downloadImage(url: String): Deferred<Bitmap?>
	fun getImage(url: String): Bitmap?
}
