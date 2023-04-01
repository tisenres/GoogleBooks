package com.example.googlebooks.favourites

import com.example.googlebooks.bookadapter.IAdapterHandler
import com.example.googlebooks.search.entity.Book
import io.reactivex.disposables.Disposable

class FavoritesPresenter(private val favoritesView: IFavoritesView) : IFavoritesPresenter, IAdapterHandler {

	private val favoritesModel: IFavoritesModel = FavoritesModel()
	private var disposable: Disposable? = null

	override fun getBooksCount(): Int = favoritesModel.getBooksCount()

	override fun getBook(position: Int): Book {
		return favoritesModel.getBook(position)
	}

	override fun onFavoritesButtonPressed(book: Book) {
		favoritesModel.deleteFavoriteBook(book)
	}

	override fun isBookFavoriteNow(book: Book): Boolean = true

	override fun onViewCreated() {
		disposable = favoritesModel.getRepositoryChangeSubject()
			.subscribe { favoritesView.reloadBookList() }
	}

	override fun onViewDestroy() {
		disposable?.dispose()
	}
}
