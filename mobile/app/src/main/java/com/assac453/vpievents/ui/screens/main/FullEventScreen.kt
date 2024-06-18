package com.assac453.vpievents.ui.screens.main

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.assac453.vpievents.R
import com.assac453.vpievents.data.entity.Event
import com.assac453.vpievents.logic.utils.PATTERN
import com.assac453.vpievents.logic.utils.convertDate
import com.assac453.vpievents.ui.graph.AppNavigation


@Composable
fun FullEventScreen(
    modifier: Modifier = Modifier,
    onScanClicked: (context: Context) -> Unit,
    context: Context
) {
    val event: Event? = AppNavigation.EventContainer.selectedEvent
    Scaffold {
        println(it)
        Card(
            modifier = modifier
                .padding(it)
//                .fillMaxSize()
                .minimumInteractiveComponentSize()
                .padding(vertical = 16.dp)
                .verticalScroll(rememberScrollState()),


            ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(15.dp)

            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = event?.image)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                placeholder(R.drawable.loading_img)
                                error(R.drawable.ic_connection_error)
                            }).build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp))
                        .drawWithContent {
                            drawContent()
                            drawRoundRect(
                                color = Color.Black.copy(alpha = 0.1f),
                                topLeft = Offset.Zero,
                                size = Size(size.width, size.height),
                                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                            )
                        },
                    contentScale = ContentScale.Crop // Масштабируем изображение до его заполнения
                )
                event?.let {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Название:",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = event.name ?: "", modifier = Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Описание:",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = event.description ?: "", modifier = Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Начало:",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = convertDate(event.startDate ?:"", "dd.MM.yyyy hh:mm"),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Конец:",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = convertDate(event.endDate ?:"", PATTERN.date),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Тип мероприятия:",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = event.eventType?.name ?: "", modifier = Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Место проведения:",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = event.address ?: "", modifier = Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Количество баллов:",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = event.points?.toString() ?: "", modifier = Modifier.weight(1f))
                    }


                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Button(
                        onClick = { onScanClicked(context) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Сканировать QR-код")
                        Icon(
                            painter = painterResource(id = R.drawable.qr_scan),
                            contentDescription = "QR scan",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

            }
        }
    }

}



