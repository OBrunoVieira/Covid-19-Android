package com.doubleb.covid19.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CountryInfo(
    @SerializedName("flag") val flagUrl: String? = null,
    val iso2: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(flagUrl)
        parcel.writeString(iso2)
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