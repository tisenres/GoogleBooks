package com.example.googlebooks.data.remote.vo

import com.example.googlebooks.app.features.search.entity.Book

data class ServerResponse(val items: List<Item>) {
	fun convertToList() = items.map { Book(it.volumeInfo.title, it.volumeInfo.description, it.volumeInfo.imageLinks?.thumbnail) }
}

data class Item (val volumeInfo: VolumeInfo)

data class VolumeInfo (val title: String, val description: String?, val imageLinks: ImageLinks?)

data class ImageLinks(val thumbnail: String?)
