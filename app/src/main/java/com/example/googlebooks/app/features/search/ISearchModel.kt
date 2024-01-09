package com.example.googlebooks.app.features.search

import android.graphics.Bitmap
import com.example.googlebooks.app.features.search.entity.Book
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.ResponseBody

interface ISearchModel {

	fun getBooks(query: String)
	fun getBooksCount(): Int
	fun getBook(position: Int): Book
	fun toggleFavoriteStatus(book: Book)
	fun isBookFavoriteNow(book: Book): Boolean
	fun getRepositoryChangeFlow(): SharedFlow<Boolean>
	fun clearDataSet()
	fun downloadImage(url: String): Bitmap?
	fun getImage(url: String): Bitmap?

}
