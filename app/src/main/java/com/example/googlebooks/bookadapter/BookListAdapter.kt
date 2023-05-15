package com.example.googlebooks.bookadapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.googlebooks.databinding.RecyclerViewItemBinding
import com.example.googlebooks.search.entity.Book
import io.reactivex.disposables.Disposable

class BookListAdapter(private val adapterHandler: IAdapterHandler): Adapter<BookListAdapter.BooksViewHolder>() {

	private val images: Map<String, Bitmap> = mutableMapOf()

	class BooksViewHolder(itemView: View, val binding: RecyclerViewItemBinding, private val adapterHandler: IAdapterHandler) : RecyclerView.ViewHolder(itemView) {
		private var disposable: Disposable? = null
		private var bitmap: Bitmap? = null

		fun getImage(book: Book): Pair<Disposable?, Bitmap?> {
			disposable = book.imageLink?.let {
				adapterHandler.getBookImage(it)
					.subscribe(
						{ response ->
							bitmap = BitmapFactory.decodeStream(response.byteStream())
						},
						{ ex ->
							ex.printStackTrace()
						}
					)
			}
			return Pair(disposable, bitmap)
		}
	}

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

		holder.binding.bookImage.setImageBitmap(holder.getImage(book).second)

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
		// TODO CLEAR IMAGES
		notifyItemRangeRemoved(0, itemCount)
	}
}