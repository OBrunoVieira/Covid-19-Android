package com.doubleb.covid19.model

import android.os.Parcel
import android.os.Parcelable

data class NewsResult(val totalResults: Int = 0, val articles: List<News>? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createTypedArrayList(News.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(totalResults)
        parcel.writeTypedList(articles)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsResult> {
        override fun createFromParcel(parcel: Parcel): NewsResult {
            return NewsResult(parcel)
        }

        override fun newArray(size: Int): Array<NewsResult?> {
            return arrayOfNulls(size)
        }
    }

}