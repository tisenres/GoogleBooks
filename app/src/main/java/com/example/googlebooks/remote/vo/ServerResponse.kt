package com.example.googlebooks.remote.vo

import com.example.googlebooks.search.entity.Book

data class ServerResponse(val items: List<Item>) {
	fun convertToList() = items.map { Book(it.id, it.volumeInfo.title, it.volumeInfo.description) }
}

data class Item (val id: String, val volumeInfo: VolumeInfo)

data class VolumeInfo (val title: String, val description: String?)
