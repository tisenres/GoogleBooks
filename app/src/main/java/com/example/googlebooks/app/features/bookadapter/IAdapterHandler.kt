package com.example.googlebooks.app.features.bookadapter

import android.graphics.Bitmap
import com.example.googlebooks.app.features.search.entity.Book

interface IAdapterHandler {
    fun getBooksCount(): Int
    fun getBook(position: Int): Book
    fun onFavoritesButtonPressed(book: Book)
    fun isBookFavoriteNow(book: Book): Boolean
    fun getBookImage(url: String): Bitmap?
}