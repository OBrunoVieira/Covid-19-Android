package com.doubleb.covid19.ui.view_holder

import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.doubleb.covid19.R
import com.doubleb.covid19.extensions.gone
import com.doubleb.covid19.extensions.visible
import com.doubleb.covid19.model.News
import com.doubleb.covid19.ui.listener.ClickListener
import kotlinx.android.synthetic.main.view_news_item.view.*

class NewsViewHolder(
    itemView: View,
    listener: ClickListener<News?>
) : RecyclerView.ViewHolder(itemView), RequestListener<Drawable> {

    private val margin = itemView.resources.getDimension(R.dimen.world_bottom_spacing).toInt()
    private var news: News? = null

    init {
        itemView.setOnClickListener {
            listener.onItemClicked(news, adapterPosition)
        }
    }

    fun bind(news: News?, isLastPosition: Boolean) = itemView.run {
        this@NewsViewHolder.news = news

        val layoutParams = (layoutParams as? RecyclerView.LayoutParams)
        layoutParams?.setMargins(
            layoutParams.leftMargin,
            layoutParams.topMargin,
            layoutParams.rightMargin,
            if (isLastPosition) margin else 0
        )

        if (news == null || news.isEmpty()) {
            news_item_image_view_placeholder.visible()

            news_item_text_view_title.visible()
            news_item_text_view_title.loading()

            news_item_text_view_source.visible()
            news_item_text_view_source.loading()

            news_item_text_view_publish_at.visible()
            news_item_text_view_publish_at.loading()

            news_item_image_view_background.setImageDrawable(null)
            return
        }

        if (!news.urlToImage.isNullOrEmpty()) {
            news_item_image_view_placeholder.gone()
            Glide.with(context)
                .load(news.urlToImage)
                .centerCrop()
                .addListener(this@NewsViewHolder)
                .into(news_item_image_view_background)
        } else {
            news_item_image_view_placeholder.visible()
        }

        if (!news.title.isNullOrEmpty()) {
            news_item_text_view_title.visible()
            news_item_text_view_title.setLoadedText(news.title)
        } else {
            news_item_text_view_title.gone()
        }

        if (!news.author.isNullOrEmpty()) {
            news_item_text_view_source.visible()
            news_item_text_view_source.setLoadedText(news.author)
        } else if (!news.source?.name.isNullOrEmpty()) {
            news_item_text_view_source.visible()
            news_item_text_view_source.setLoadedText(news.source?.name)
        } else {
            news_item_text_view_source.gone()
        }

        if (!news.publishedAt.isNullOrEmpty()) {
            news_item_text_view_publish_at.visible()
            news_item_text_view_publish_at.setLoadedText(news.publishedAt)
        } else {
            news_item_text_view_publish_at.gone()
        }
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        itemView.news_item_image_view_placeholder.gone()
        return false
    }
}