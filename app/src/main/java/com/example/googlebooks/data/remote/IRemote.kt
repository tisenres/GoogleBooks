package com.example.googlebooks.data.remote

import com.example.googlebooks.app.features.search.entity.Book
import io.reactivex.Single
import okhttp3.ResponseBody

interface IRemote {
	suspend fun fetchBooks(query: String): List<Book>
    suspend fun fetchImage(url: String): ResponseBody
}
