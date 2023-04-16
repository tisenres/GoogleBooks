package com.example.googlebooks.remote

import android.util.Log
import com.example.googlebooks.search.entity.Book
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream

object Remote: IRemote {

	private const val BASE_HOST = "https://www.googleapis.com"

	private val api: GoogleBooksAPI = Retrofit.Builder()
		.baseUrl(BASE_HOST)
		.addConverterFactory(GsonConverterFactory.create())
		.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
		.build()
		.create(GoogleBooksAPI::class.java)

	override fun fetchBooks(query: String): Single<List<Book>> {
		return api.fetchBooks(query)
			.subscribeOn(Schedulers.io())
			.map { it.convertToList() }
	}

    override fun fetchImage(url: String): Single<InputStream> {

//		val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
//		connection.doInput = true
//		connection.connect()

//		return Single.just(connection.inputStream)
//			.subscribeOn(Schedulers.io())
		Log.d("My tag", "We're in Remote")

		val imageApi: GoogleBooksAPI = Retrofit.Builder()
			.baseUrl("http://books.google.com/books/")
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.build()
			.create(GoogleBooksAPI::class.java)

		return imageApi.fetchImage("$url/").subscribeOn(Schedulers.io())

    }
}