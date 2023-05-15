package com.example.googlebooks.search

import com.example.googlebooks.search.entity.Book
import io.reactivex.Single
import okhttp3.ResponseBody

interface ISearchPresenter {
	fun onSearchButtonPressed(query: String)
	fun onFavoritesButtonPressed(book: Book)
	fun onViewCreated()
	fun onViewDestroy()
	fun getBookImage(url: String): Single<ResponseBody>
}
