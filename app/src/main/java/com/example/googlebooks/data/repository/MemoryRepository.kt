package com.example.googlebooks.data.repository

import com.example.googlebooks.app.features.search.entity.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object MemoryRepository: AbstractRepository() {

	private val _repoChangedFlow = MutableSharedFlow<Boolean>()
	val repoChangedFlow = _repoChangedFlow.asSharedFlow()

	private val storage: MutableList<Book> = mutableListOf()
	private val repoScope = CoroutineScope(Dispatchers.IO)

	override fun save(book: Book) {
		storage.add(book)

		repoScope.launch {
			_repoChangedFlow.emit(true)
		}
	}

	override fun delete(book: Book) {
		storage.remove(book)
		repoScope.launch {
			_repoChangedFlow.emit(true)
		}
	}

	override fun get(position: Int): Book {
		return storage[position]
	}

	override fun contains(book: Book): Boolean {
		return storage.contains(book)
	}

	override fun getRepoSize(): Int {
		return storage.size
	}
}