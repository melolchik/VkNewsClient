package ru.melolchik.vknewsclient.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


enum class StatisticType{
    VIEWS,
    COMMENTS,
    SHARES,
    LIKES
}

@Parcelize
data class StatisticItem(
    val type : StatisticType,
    val count : Int = 0
) : Parcelable
