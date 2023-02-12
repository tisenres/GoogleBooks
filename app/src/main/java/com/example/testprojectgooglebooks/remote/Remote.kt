package com.example.testprojectgooglebooks.remote

import com.example.testprojectgooglebooks.search.entity.Book
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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
}