package com.example.googlebooks.app.features.search

interface ISearchView {
	fun reloadBookList()
	fun showErrorMessage(error: String)
	fun showEmptyQueryMess()
	fun startProgressBar()
	fun stopProgressBar()
    fun clearBookList()
}
