package com.example.googlebooks.bookadapter

import com.example.googlebooks.search.entity.Book
import io.reactivex.Single
import java.io.InputStream

interface IAdapterHandler {
	fun getBooksCount(): Int
	fun getBook(position: Int): Book
	fun onFavoritesButtonPressed(book: Book)
	fun isBookFavoriteNow(book: Book): Boolean
	fun getBookImage(url: String): Single<InputStream>
}