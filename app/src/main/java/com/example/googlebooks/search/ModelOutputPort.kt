package com.example.googlebooks.search

interface ModelOutputPort {
	fun onBookReceived()
	fun onFetchError(message: String)
}