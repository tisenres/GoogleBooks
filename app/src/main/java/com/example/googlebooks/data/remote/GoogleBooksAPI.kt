package com.example.googlebooks.data.remote

import com.example.googlebooks.data.remote.vo.ServerResponse
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GoogleBooksAPI {

	@GET("/books/v1/volumes")
	suspend fun fetchBooks(@Query("q") query: String): ServerResponse

	@GET
	suspend fun fetchImage(@Url url: String): ResponseBody
}