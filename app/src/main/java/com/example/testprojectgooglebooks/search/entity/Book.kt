package com.example.testprojectgooglebooks.search.entity

data class Book(val title: String,
				val description: String?,
			   	val isFavorite: Boolean = false
			   )