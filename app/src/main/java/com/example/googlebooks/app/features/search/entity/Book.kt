package com.example.googlebooks.app.features.search.entity

import android.graphics.Bitmap

data class Book(
	val title: String,
	val description: String?,
	val imageLink: String?,
	var imageBitmap: Bitmap? = null
)