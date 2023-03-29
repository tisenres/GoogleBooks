package com.example.googlebooks.search.entity

import io.realm.RealmObject

data class Book(
				val id: String,
				val title: String,
				val description: String?,
			   	var isFavorite: Boolean = false
			   ): RealmObject()