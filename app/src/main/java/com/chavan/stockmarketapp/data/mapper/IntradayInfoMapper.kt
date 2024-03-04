package com.chavan.stockmarketapp.data.mapper

import com.chavan.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.chavan.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntradayInfoDto.toIntradayInfo() : IntradayInfo{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timstamp,formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close
    )
}

