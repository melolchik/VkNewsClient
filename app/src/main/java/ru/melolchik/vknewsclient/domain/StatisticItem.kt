package ru.melolchik.vknewsclient.domain


enum class StatisticType{
    VIEWS,
    COMMENTS,
    SHARES,
    LIKES
}
data class StatisticItem(
    val type : StatisticType,
    val count : Int = 0
)
