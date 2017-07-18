package com.pmvb.keddit.commons

import android.os.Parcel
import android.os.Parcelable
import com.pmvb.keddit.commons.adapter.AdapterConstants
import com.pmvb.keddit.commons.adapter.ViewType
import com.pmvb.keddit.commons.extensions.createParcel

data class RedditNewsPage(
        val after: String,
        val before: String,
        val news: List<RedditNewsItem>
): Parcelable {
    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { RedditNewsPage(it) }
    }

    protected constructor(parcel: Parcel): this(
            parcel.readString(),
            parcel.readString(),
            mutableListOf<RedditNewsItem>().apply {
                parcel.readTypedList(this, RedditNewsItem.CREATOR)
            }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(after)
        dest.writeString(before)
        dest.writeTypedList(news)
    }

    override fun describeContents(): Int = 0
}

data class RedditNewsItem(
        val author: String,
        val title: String,
        val numComments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String
): ViewType, Parcelable {
    override fun getViewType() = AdapterConstants.NEWS

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { RedditNewsItem(it) }
    }

    protected constructor(parcel: Parcel): this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(author)
        dest.writeString(title)
        dest.writeInt(numComments)
        dest.writeLong(created)
        dest.writeString(thumbnail)
        dest.writeString(url)
    }

    override fun describeContents(): Int = 0
}