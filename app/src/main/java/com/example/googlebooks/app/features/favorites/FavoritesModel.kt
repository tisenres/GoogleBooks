package com.example.googlebooks.app.features.favorites

import com.example.googlebooks.data.repository.MemoryRepository
import com.example.googlebooks.app.features.search.entity.Book
import kotlinx.coroutines.flow.SharedFlow

class FavoritesModel : IFavoritesModel {

	private val repo = MemoryRepository

	override fun getBooksCount(): Int = repo.getRepoSize()

	override fun getBook(position: Int): Book {
		return repo.get(position)
	}

	override fun getRepositoryChangeFlow(): SharedFlow<Boolean> {
		return repo.repoChangedFlow
	}

	override fun deleteFavoriteBook(book: Book) {
		repo.delete(book)
	}
}

