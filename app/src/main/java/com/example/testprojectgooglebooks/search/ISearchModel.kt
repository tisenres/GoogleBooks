package com.example.testprojectgooglebooks.search

import com.example.testprojectgooglebooks.search.entity.Book

interface ISearchModel {
	fun getBooks(query: String)
	fun getBooksCount(): Int
	fun getBook(position: Int): Book

}
