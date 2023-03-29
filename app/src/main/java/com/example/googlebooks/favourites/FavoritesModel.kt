package com.example.googlebooks.favourites

import com.example.googlebooks.repository.MemoryRepository
import com.example.googlebooks.search.entity.Book
import io.reactivex.subjects.BehaviorSubject

class FavoritesModel : IFavoritesModel {

	private val repo = MemoryRepository

	override fun getBooksCount(): Int = repo.getRepoSize()

	override fun getBook(position: Int): Book {
		return repo.get(position)
	}

	override fun getRepositoryChangeSubject(): BehaviorSubject<Boolean> {
		return repo.behaviorSubject
	}

	override fun deleteFavoriteBook(book: Book) {
		repo.delete(book)
	}


}

