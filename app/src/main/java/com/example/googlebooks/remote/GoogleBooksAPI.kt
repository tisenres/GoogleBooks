package com.example.googlebooks.remote

import com.example.googlebooks.remote.vo.ServerResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.io.InputStream

interface GoogleBooksAPI {

	@GET("/books/v1/volumes")
	fun fetchBooks(@Query("q") query: String): Single<ServerResponse>

	@GET
	fun fetchImage(@Url url: String): Single<InputStream>
}