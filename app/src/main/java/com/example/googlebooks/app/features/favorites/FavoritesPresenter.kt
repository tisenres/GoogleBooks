package com.example.googlebooks.app.features.favorites

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.graphics.createBitmap
import com.example.googlebooks.R
import com.example.googlebooks.app.features.bookadapter.IAdapterHandler
import com.example.googlebooks.app.features.search.entity.Book
import io.reactivex.disposables.Disposable

class FavoritesPresenter(private val favoritesView: IFavoritesView) : IFavoritesPresenter,
	IAdapterHandler {

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
			.subscribe({ favoritesView.reloadBookList() },
						{ it.message?.let { error -> Log.e("My log", error) } })
	}

	override fun onViewDestroy() {
		disposable?.dispose()
	}
}
