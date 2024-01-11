package com.example.googlebooks.data.remote

import com.example.googlebooks.app.features.search.entity.Book
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Remote: IRemote {

	private const val BASE_HOST = "https://www.googleapis.com"

	private val api: GoogleBooksAPI = Retrofit.Builder()
		.baseUrl(BASE_HOST)
		.addConverterFactory(GsonConverterFactory.create())
		.build()
		.create(GoogleBooksAPI::class.java)

	override suspend fun fetchBooks(query: String): List<Book> {
		return api.fetchBooks(query).convertToList()
	}

    override suspend fun fetchImage(url: String): ResponseBody {
		return api.fetchImage(url)
	}
}