package com.example.googlebooks.search

import com.example.googlebooks.search.entity.Book

interface ISearchModel {
	fun getBooks(query: String)
	fun getBooksCount(): Int
	fun getBook(position: Int): Book
	fun toggleFavoriteStatus(book: Book)

}
