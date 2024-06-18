package com.assac453.vpievents.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.assac453.vpievents.data.entity.Event
import com.assac453.vpievents.logic.utils.PATTERN
import com.assac453.vpievents.logic.utils.convertDate
import com.assac453.vpievents.ui.graph.AppNavigation
import com.assac453.vpievents.ui.theme.Typography

@Composable
fun EventCard(event: Event, modifier: Modifier = Modifier, navController: NavHostController){
    val fontSize = 12.sp
    Card(
        modifier = modifier
            .padding(6.dp)
            .fillMaxSize()
            .clickable { navigateToEventDetail(navController, event) },
//        onClick = onCardClicked

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Column {
                Text(text = "Начало", fontSize = fontSize)
                event.startDate?.let {string ->
                    Text(text = convertDate(string, PATTERN.date), fontSize = fontSize)

                }
                Spacer(modifier.size(15.dp))
                Text(text = "Конец", fontSize = fontSize)
                event.endDate?.let {string ->
                    Text(text = convertDate(string, PATTERN.date), fontSize = fontSize)
                }
            }
            Column(modifier = Modifier.padding(20.dp)) {
                event.name?.let { Text(text = it, fontWeight = FontWeight.Bold, fontSize = Typography.titleMedium.fontSize) }
                Spacer(modifier.size(15.dp))
                event.description?.let { Text(text = it.take(100).plus("...")) }
            }
        }
    }
}

@Composable
fun EventGridScreen(events: List<Event>, modifier: Modifier = Modifier, navController: NavHostController){
    LazyVerticalGrid(columns = GridCells.Adaptive(300.dp), contentPadding = PaddingValues(10.dp)){
        itemsIndexed(events){_, event ->
            EventCard(event = event, modifier = modifier, navController = navController)
        }
    }
}

fun navigateToEventDetail(navController: NavHostController, event: Event){
    AppNavigation.EventContainer.selectedEvent = event
    navController.navigate(AppNavigation.EVENT_DESCRIPTION)
}