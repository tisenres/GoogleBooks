package com.example.googlebooks.app.features.favorites

import com.example.googlebooks.app.features.search.entity.Book
import kotlinx.coroutines.flow.SharedFlow

interface IFavoritesModel {
	fun getBooksCount(): Int
	fun getBook(position: Int): Book
	fun getRepositoryChangeFlow(): SharedFlow<Boolean>
	fun deleteFavoriteBook(book: Book)
}
