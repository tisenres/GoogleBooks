package com.example.googlebooks.app.features.favorites

import android.util.Log
import com.example.googlebooks.app.features.bookadapter.IAdapterHandler
import com.example.googlebooks.app.features.search.entity.Book
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesPresenter(private val favoritesView: IFavoritesView) : IFavoritesPresenter,
	IAdapterHandler {

	private val favoritesModel: IFavoritesModel = FavoritesModel()
	private var disposable: Disposable? = null
	private val presenterScope = CoroutineScope(Dispatchers.Main)

	override fun getBooksCount(): Int = favoritesModel.getBooksCount()

	override fun getBook(position: Int): Book {
		return favoritesModel.getBook(position)
	}

	override fun onFavoritesButtonPressed(book: Book) {
		favoritesModel.deleteFavoriteBook(book)
	}

	override fun isBookFavoriteNow(book: Book): Boolean = true

	override fun onViewCreated() {
		presenterScope.launch {
			favoritesModel.getRepositoryChangeFlow()
				.collect {
					Log.d("ChangedFLOWLOW", "Repo changed")
					favoritesView.reloadBookList()
				}
		}
//		disposable = favoritesModel.getRepositoryChangeFlow()
//			.subscribe({ favoritesView.reloadBookList() },
//						{ it.message?.let { error -> Log.e("My log", error) } })
	}

	override fun onViewDestroy() {
		disposable?.dispose()
	}
}
