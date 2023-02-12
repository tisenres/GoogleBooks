package com.example.googlebooks.remote

import com.example.googlebooks.search.entity.Book
import io.reactivex.Single

interface IRemote {
	fun fetchBooks(query: String): Single<List<Book>>

}
