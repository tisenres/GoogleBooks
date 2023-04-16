package com.example.googlebooks.bookadapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.googlebooks.databinding.RecyclerViewItemBinding
import com.example.googlebooks.search.entity.Book

class BookListAdapter(private val adapterHandler: IAdapterHandler): Adapter<BookListAdapter.BooksViewHolder>() {

	private val images: Map<String, Bitmap> = mutableMapOf()

	class BooksViewHolder(itemView: View, val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(itemView)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
		val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context))
		val viewHolder = BooksViewHolder(binding.root, binding)

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

		// Если в Map нет Bitmap, то оформляем подписку на Single и достаём дефолтовое значение
		images.getOrDefault(book.imageLink, getImage(book))

		if (adapterHandler.isBookFavoriteNow(book)) {
			holder.binding.favButton.setImageState(listOf(android.R.attr.state_checked).toIntArray(),true)
		} else {
			holder.binding.favButton.setImageState(emptyArray<Int>().toIntArray(), false)
		}
	}

	private fun getImage(book: Book) {

		lateinit var bitmap: Bitmap

		val disposable = book.imageLink?.let { it ->
			adapterHandler.getBookImage(it)
				.subscribe(
					{ inputStream ->
						Log.d("My tag", "We're in Adapter")
						bitmap = BitmapFactory.decodeStream(inputStream)
						BitmapFactory.decodeStream(inputStream)
					},
					{ ex ->
						ex.printStackTrace()
					}
				)
		}

	}


	override fun getItemCount(): Int {
		return adapterHandler.getBooksCount()
	}

	private fun getItem(position: Int): Book {
		return adapterHandler.getBook(position)
	}

	fun clearItems() {
		// TODO CLEAR IMAGES
		notifyItemRangeRemoved(0, itemCount)
	}
}