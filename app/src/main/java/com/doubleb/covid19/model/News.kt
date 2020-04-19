package com.doubleb.covid19.model

import android.os.Parcel
import android.os.Parcelable

data class News(
    val source: NewsSource? = null,
    val author: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(NewsSource::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(source, flags)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<News> {
        override fun createFromParcel(parcel: Parcel): News {
            return News(parcel)
        }

        override fun newArray(size: Int): Array<News?> {
            return arrayOfNulls(size)
        }
    }

    fun isEmpty() = source == null
            && author == null
            && title == null
            && url == null
            && urlToImage == null
            && publishedAt == null
}