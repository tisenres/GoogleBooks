package com.example.googlebooks.search

import android.graphics.Bitmap
import com.example.googlebooks.search.entity.Book
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody

interface ISearchModel {

	fun getBooks(query: String)
	fun getBooksCount(): Int
	fun getBook(position: Int): Book
	fun toggleFavoriteStatus(book: Book)
	fun isBookFavoriteNow(book: Book): Boolean
	fun getRepositoryChangeSubject(): Observable<Boolean>
	fun clearDataSet()
	fun downloadImage(url: String): Bitmap?
	fun getImage(url: String): Bitmap?

}
