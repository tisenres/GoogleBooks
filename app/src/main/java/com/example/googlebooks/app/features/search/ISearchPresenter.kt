package com.example.googlebooks.app.features.search

import com.example.googlebooks.app.features.search.entity.Book

interface ISearchPresenter {
	fun onSearchButtonPressed(query: String)
	fun onFavoritesButtonPressed(book: Book)
	fun onViewCreated()
	fun onViewDestroy()
}
