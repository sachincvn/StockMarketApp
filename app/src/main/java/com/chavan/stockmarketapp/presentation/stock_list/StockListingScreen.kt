package com.chavan.stockmarketapp.presentation.stock_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.chavan.stockmarketapp.presentation.destinations.StockInfoScreenDestination
import com.chavan.stockmarketapp.presentation.stock_list.components.StockItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun StockListingScreen(
    navigator : DestinationsNavigator,
    viewModel: StockListingViewModel = hiltViewModel()
){
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)
    val state = viewModel.state

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(StockListingEvents.OnSearchQueryChange(it))
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Search..",
                    )
            },
            maxLines = 1,
            singleLine = true
        )
        
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(StockListingEvents.Refresh) }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(state.stocks.size){i->
                    StockItem(
                        stock = state.stocks[i],
                        modifier = Modifier.fillMaxSize()
                            .clickable{
                                navigator.navigate(StockInfoScreenDestination(symbol = state.stocks[i].symbol))
                            }
                            .padding(16.dp),
                        )
                    if (i<state.stocks.size){
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }

}