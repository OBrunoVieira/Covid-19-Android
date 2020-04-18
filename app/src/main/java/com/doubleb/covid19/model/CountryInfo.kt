package com.doubleb.covid19.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CountryInfo(@SerializedName("flag") val flagUrl: String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(flagUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryInfo> {
        override fun createFromParcel(parcel: Parcel): CountryInfo {
            return CountryInfo(parcel)
        }

        override fun newArray(size: Int): Array<CountryInfo?> {
            return arrayOfNulls(size)
        }
    }
}