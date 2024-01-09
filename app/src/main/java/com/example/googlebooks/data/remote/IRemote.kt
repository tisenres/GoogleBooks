package com.example.googlebooks.data.remote

import com.example.googlebooks.app.features.search.entity.Book
import io.reactivex.Single
import okhttp3.ResponseBody

interface IRemote {
	fun fetchBooks(query: String): Single<List<Book>>
    fun fetchImage(url: String): Single<ResponseBody>
}
