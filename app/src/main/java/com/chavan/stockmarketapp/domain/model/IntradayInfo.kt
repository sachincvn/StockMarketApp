package com.chavan.stockmarketapp.domain.model

import java.time.LocalDateTime

data class IntradayInfo(
    val date: LocalDateTime,
    val close : Double
)
