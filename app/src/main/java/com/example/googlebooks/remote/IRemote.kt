package com.example.googlebooks.remote

import com.example.googlebooks.search.entity.Book
import io.reactivex.Single
import java.io.InputStream

interface IRemote {
	fun fetchBooks(query: String): Single<List<Book>>
    fun fetchImage(url: String): Single<InputStream>

}
