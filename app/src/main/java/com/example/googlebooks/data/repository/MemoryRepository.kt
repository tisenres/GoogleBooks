package com.example.googlebooks.data.repository

import com.example.googlebooks.app.features.search.entity.Book
import io.reactivex.subjects.BehaviorSubject

object MemoryRepository: AbstractRepository() {

	val behaviorSubject = BehaviorSubject.create<Boolean>()

	private val storage: MutableList<Book> = mutableListOf()

	override fun save(book: Book) {
		storage.add(book)
		behaviorSubject.onNext(true)
	}

	override fun delete(book: Book) {
		storage.remove(book)
		behaviorSubject.onNext(true)
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