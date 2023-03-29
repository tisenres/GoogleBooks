package com.example.googlebooks.repository

import com.example.googlebooks.search.entity.Book

abstract class AbstractRepository {
	abstract fun save(book: Book)
	abstract fun delete(book: Book)
	abstract fun get(position: Int): Book
	abstract fun getRepoSize(): Int
	abstract fun contains(book: Book): Boolean
}