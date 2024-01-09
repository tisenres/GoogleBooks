package com.example.googlebooks.app.features.search

interface ISearchView {
	fun reloadBookList()
	fun showErrorMess(error: String)
	fun showEmptyQueryMess()
	fun startProgressBar()
	fun stopProgressBar()
    fun clearBookList()
}
