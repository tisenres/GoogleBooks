package com.example.googlebooks.app.features.search

interface ModelOutputPort {
	fun onBooksReceived()
	fun onFetchError(message: String)
	fun onImageReceived()
}