package com.example.testprojectgooglebooks.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.testprojectgooglebooks.databinding.RecyclerViewItemBinding
import com.example.testprojectgooglebooks.search.entity.Book

class BooksAdapter(private val searchPresenter: ISearchPresenter): Adapter<BooksAdapter.BooksViewHolder>() {

	class BooksViewHolder(itemView: View, val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(itemView)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
		val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context))
		return BooksViewHolder(binding.root, binding)
	}

	override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
		val book: Book = searchPresenter.getBook(position)

		holder.binding.title.text = book.title
		holder.binding.description.text = book.description
	}

	override fun getItemCount(): Int {
		return searchPresenter.getBooksCount()
	}
}