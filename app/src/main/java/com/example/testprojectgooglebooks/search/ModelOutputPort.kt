package com.example.testprojectgooglebooks.search

interface ModelOutputPort {
	fun onBookReceived()
	fun onFetchError(message: String)
}