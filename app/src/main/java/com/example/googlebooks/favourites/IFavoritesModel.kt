package com.example.googlebooks.favourites

import com.example.googlebooks.search.entity.Book
import io.reactivex.subjects.BehaviorSubject

interface IFavoritesModel {
	fun getBooksCount(): Int
	fun getBook(position: Int): Book
	fun getRepositoryChangeSubject(): BehaviorSubject<Boolean>
	fun deleteFavoriteBook(book: Book)

}
