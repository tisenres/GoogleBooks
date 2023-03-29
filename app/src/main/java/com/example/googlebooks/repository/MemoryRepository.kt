package com.example.googlebooks.repository

import com.example.googlebooks.search.entity.Book
import io.reactivex.subjects.BehaviorSubject

object MemoryRepository: AbstractRepository() {

	val behaviorSubject = BehaviorSubject.create<Boolean>()

	private val storage: MutableList<Book> = mutableListOf()

	override fun save(book: Book) {
		storage.add(book)
		behaviorSubject.onNext(true)

		println("Saved book: ${book.title} ID: ${book.id}")
	}

	override fun delete(book: Book) {
		storage.remove(book)
		behaviorSubject.onNext(true)

		println("Deleted book: ${book.title} ID: ${book.id}")
	}

	override fun get(position: Int): Book {
		println("Get book with position $position")
		return storage[position]
	}

	override fun contains(book: Book): Boolean {
		return storage.contains(book)
	}

	override fun getRepoSize(): Int {
		return storage.size
	}
}