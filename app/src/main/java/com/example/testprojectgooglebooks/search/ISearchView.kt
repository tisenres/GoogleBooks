package com.example.testprojectgooglebooks.search

interface ISearchView {
	fun reloadBookList()
	fun showErrorMess(error: String)
	fun showEmptyQueryMess()
}
