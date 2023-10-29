package com.example.googlebooks.data.remote

import com.example.googlebooks.data.remote.vo.ServerResponse
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GoogleBooksAPI {

	@GET("/books/v1/volumes")
	fun fetchBooks(@Query("q") query: String): Single<ServerResponse>

	@GET
	fun fetchImage(@Url url: String): Single<ResponseBody>
}