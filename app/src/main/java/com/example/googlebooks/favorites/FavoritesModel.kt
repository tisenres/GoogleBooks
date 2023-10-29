package com.example.googlebooks.favorites

import com.example.googlebooks.data.repository.MemoryRepository
import com.example.googlebooks.search.entity.Book
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class FavoritesModel : IFavoritesModel {

	private val repo = MemoryRepository

	override fun getBooksCount(): Int = repo.getRepoSize()

	override fun getBook(position: Int): Book {
		return repo.get(position)
	}

	override fun getRepositoryChangeSubject(): Observable<Boolean> {
		return repo.behaviorSubject
			.observeOn(AndroidSchedulers.mainThread())
	}

	override fun deleteFavoriteBook(book: Book) {
		repo.delete(book)
	}


}

