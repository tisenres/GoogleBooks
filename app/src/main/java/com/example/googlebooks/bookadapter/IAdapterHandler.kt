package com.example.googlebooks.bookadapter

import com.example.googlebooks.search.entity.Book

interface IAdapterHandler {
	fun getBooksCount(): Int
	fun getBook(position: Int): Book
	fun onFavoritesButtonPressed(book: Book)
}
