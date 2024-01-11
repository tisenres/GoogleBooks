package com.example.googlebooks.app.features.bookadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.googlebooks.databinding.RecyclerViewItemBinding
import com.example.googlebooks.app.features.search.entity.Book

class BookListAdapter(private val adapterHandler: IAdapterHandler): Adapter<BookListAdapter.BooksViewHolder>() {

	class BooksViewHolder(itemView: View, val binding: RecyclerViewItemBinding, private val adapterHandler: IAdapterHandler) : RecyclerView.ViewHolder(itemView)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
		val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context))
		val viewHolder = BooksViewHolder(binding.root, binding, adapterHandler)

		viewHolder.binding.favButton.setOnClickListener {
			val pos = viewHolder.adapterPosition
			if (pos != NO_POSITION) {
				adapterHandler.onFavoritesButtonPressed(getItem(pos))
			}
		}
		return viewHolder
	}

	override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
		val book: Book = getItem(position)

		holder.binding.title.text = book.title
		holder.binding.description.text = book.description
		holder.binding.bookImage.setImageBitmap(book.imageBitmap)

		if (adapterHandler.isBookFavoriteNow(book)) {
			holder.binding.favButton.setImageState(listOf(android.R.attr.state_checked).toIntArray(),true)
		} else {
			holder.binding.favButton.setImageState(emptyArray<Int>().toIntArray(), false)
		}
	}

	override fun getItemCount(): Int {
		return adapterHandler.getBooksCount()
	}

	private fun getItem(position: Int): Book {
		return adapterHandler.getBook(position)
	}

	fun clearItems() {
		notifyItemRangeRemoved(0, itemCount)
	}
}