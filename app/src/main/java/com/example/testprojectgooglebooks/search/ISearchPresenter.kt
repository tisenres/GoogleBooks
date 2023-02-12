package com.example.testprojectgooglebooks.search

import com.example.testprojectgooglebooks.search.entity.Book

interface ISearchPresenter {
	fun onSearchButtonPressed(query: String)
	fun getBook(position: Int): Book
	fun getBooksCount(): Int
}
