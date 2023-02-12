package com.example.googlebooks.search

import com.example.googlebooks.search.entity.Book

interface ISearchPresenter {
	fun onSearchButtonPressed(query: String)
	fun getBook(position: Int): Book
	fun getBooksCount(): Int
}
