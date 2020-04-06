package com.doubleb.covid19.model

import android.os.Parcel
import android.os.Parcelable

data class TimeLineResult(val date: String?, val cases: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeInt(cases)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<TimeLineResult> {
        override fun createFromParcel(parcel: Parcel): TimeLineResult {
            return TimeLineResult(parcel)
        }

        override fun newArray(size: Int): Array<TimeLineResult?> {
            return arrayOfNulls(size)
        }
    }
}