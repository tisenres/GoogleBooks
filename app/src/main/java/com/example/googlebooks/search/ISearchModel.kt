package com.example.googlebooks.search

import com.example.googlebooks.search.entity.Book
import io.reactivex.subjects.BehaviorSubject

interface ISearchModel {
	fun getBooks(query: String)
	fun getBooksCount(): Int
	fun getBook(position: Int): Book
	fun toggleFavoriteStatus(book: Book)
	fun isBookFavoriteNow(book: Book): Boolean

	fun getRepositoryChangeSubject(): BehaviorSubject<Boolean>

}
