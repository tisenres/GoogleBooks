package com.example.googlebooks.app.features.search

interface ModelOutputPort {
	fun onBookReceived()
	fun onFetchError(message: String)
}