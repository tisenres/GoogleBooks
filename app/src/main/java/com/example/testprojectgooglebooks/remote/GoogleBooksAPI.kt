package com.example.testprojectgooglebooks.remote

import com.example.testprojectgooglebooks.remote.vo.ServerResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksAPI {

	@GET("/books/v1/volumes")
	fun fetchBooks(@Query("q") query: String): Single<ServerResponse>
}