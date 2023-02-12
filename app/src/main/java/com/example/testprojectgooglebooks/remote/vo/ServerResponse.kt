package com.example.testprojectgooglebooks.remote.vo

import com.example.testprojectgooglebooks.search.entity.Book

data class ServerResponse(val items: List<Item>) {
	fun convertToList() = items.map { Book(it.volumeInfo.title, it.volumeInfo.description) }
}

data class Item (val volumeInfo: VolumeInfo)

data class VolumeInfo (val title: String, val description: String?)
