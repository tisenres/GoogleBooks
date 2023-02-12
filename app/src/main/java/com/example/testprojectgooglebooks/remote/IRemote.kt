package com.example.testprojectgooglebooks.remote

import com.example.testprojectgooglebooks.search.entity.Book
import io.reactivex.Single

interface IRemote {
	fun fetchBooks(query: String): Single<List<Book>>

}
