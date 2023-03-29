package com.example.googlebooks.bookadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.googlebooks.databinding.RecyclerViewItemBinding
import com.example.googlebooks.search.entity.Book

class SearchBooksAdapter(private val adapterHandler: IAdapterHandler): Adapter<SearchBooksAdapter.BooksViewHolder>() {

	class BooksViewHolder(itemView: View, val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(itemView)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
		val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context))
		return BooksViewHolder(binding.root, binding)
	}

	override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
		val book: Book = adapterHandler.getBook(position)

		holder.binding.title.text = book.title
		holder.binding.description.text = book.description

		holder.binding.favButton.setOnClickListener {
			adapterHandler.onFavoritesButtonPressed(book)
		}
	}

	override fun getItemCount(): Int {
		return adapterHandler.getBooksCount()
	}
}