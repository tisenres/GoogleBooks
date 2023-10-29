package com.example.googlebooks.favorites

import com.example.googlebooks.search.entity.Book
import io.reactivex.Observable

interface IFavoritesModel {
	fun getBooksCount(): Int
	fun getBook(position: Int): Book
	fun getRepositoryChangeSubject(): Observable<Boolean>
	fun deleteFavoriteBook(book: Book)

}
