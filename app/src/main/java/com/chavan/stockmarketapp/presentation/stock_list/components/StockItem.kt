package com.chavan.stockmarketapp.presentation.stock_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chavan.stockmarketapp.domain.model.StockListing

@Composable
fun StockItem(
    stock : StockListing,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stock.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                    )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stock.exchange,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onBackground
                    )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "(${stock.symbol})",
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}