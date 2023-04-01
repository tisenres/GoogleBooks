package com.example.googlebooks.search

import com.example.googlebooks.search.entity.Book

interface ISearchPresenter {
	fun onSearchButtonPressed(query: String)
	fun onFavoritesButtonPressed(book: Book)
	fun onViewCreated()
	fun onViewDestroy()
}
