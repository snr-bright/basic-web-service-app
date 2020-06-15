package com.bright.bookpluralsight

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


/**
 * Created by Monarchy on 09/10/2017.
 */


class FeedAdapter(private var items: List<Book>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val bannerConstant = 100

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(viewGroup.context)
        when (viewType) {
            bannerConstant -> {
                val viewHolderItem = inflater.inflate(R.layout.feed_item_book, viewGroup, false)
                viewHolder = ViewHolderBook(viewHolderItem)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            bannerConstant -> {
                val viewHolderMallBanner = holder as ViewHolderBook
                configureViewHolder(viewHolderMallBanner, items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return this.items.size
    }


    override fun getItemViewType(position: Int): Int {
        return when {
            items[position] is Book -> bannerConstant
            else -> -1
        }
    }


    //MARK: configure the device
    private fun configureViewHolder(viewHolder: ViewHolderBook, data: Book) {
        val textViewTitle = viewHolder.textViewTitle
        val textViewSummary = viewHolder.textViewSummary
        val textViewMore = viewHolder.textViewMore
        val imageMedia = viewHolder.imageViewBookCover
        val circleImageViewAuthor = viewHolder.circleImageViewAuthor

        if (data.imageLink.isNullOrEmpty()) {
            val image = getLetterView(viewHolder.itemView.context, data.title, false)
            val imageTwo = getLetterView(viewHolder.itemView.context, data.title, true)
            circleImageViewAuthor?.setImageDrawable(imageTwo)
            imageMedia?.setImageDrawable(image)
        } else {
            Picasso.get().load(data.imageLink).resize(100, 200).centerCrop().into(imageMedia)
            Picasso.get().load(data.thumbnail).resize(50, 50).centerCrop()
                .into(circleImageViewAuthor)
        }

        textViewTitle?.text = data.title
        textViewSummary?.text = data.description
        textViewMore?.text = data.publishedDate
    }

    //MARK: generate the letter image drawable
    fun getLetterView(activity: Context, letter: String, roundShape: Boolean): TextDrawable {
        val font = ResourcesCompat.getFont(activity, R.font.roboto_thin)
        val color = ColorGenerator.MATERIAL.getColor(letter)
        val builderBase = TextDrawable.builder()
            .beginConfig()
            .withBorder(1)
            .useFont(font)
            .fontSize(24)
            .bold()
            .toUpperCase()
            .endConfig()

        var builder = builderBase.rect()
        if (roundShape) {
            builder = builderBase.round()
        }
        return builder.build(letter.first().toString(), color)
    }

    class ViewHolderBook(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewBookCover: ImageView? = null
        var circleImageViewAuthor: CircleImageView? = null
        var textViewTitle: TextView? = null
        var textViewSummary: TextView? = null
        var textViewMore: TextView? = null

        init {
            imageViewBookCover = itemView.findViewById(R.id.imageViewBookCover)
            circleImageViewAuthor = itemView.findViewById(R.id.circleImageViewAuthor)
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
            textViewSummary = itemView.findViewById(R.id.textViewSummary)
            textViewMore = itemView.findViewById(R.id.textViewMore)
        }
    }

}