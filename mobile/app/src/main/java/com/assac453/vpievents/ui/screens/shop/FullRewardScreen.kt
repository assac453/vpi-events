package com.assac453.vpievents.ui.screens.shop

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.assac453.vpievents.R
import com.assac453.vpievents.data.entity.Reward
import com.assac453.vpievents.ui.graph.AppNavigation

@Composable
fun FullRewardScreen(
    context: Context,
    modifier: Modifier = Modifier,
    onOrderClicked: () -> Unit
) {
    val reward: Reward? = AppNavigation.RewardContainer.selectedReward

    val openDialog = remember { mutableStateOf(false) }
    Scaffold {
        Card(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
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
                        ImageRequest.Builder(LocalContext.current).data(data = reward?.url)
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
                                size = androidx.compose.ui.geometry.Size(size.width, size.height),
                                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                            )
                        },
                    contentScale = ContentScale.Crop // Масштабируем изображение до его заполнения
                )
                Text(
                    text = "${reward?.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "Стоимость (в баллах): ${reward?.cost} ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Button(
                    onClick = onOrderClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(text = "Заказать")
                    Icon(
                        Icons.Rounded.ShoppingCart,
                        contentDescription = "Заказать",
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "Как это работает?",
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue
                    ),
                    modifier = Modifier
                        .clickable {
                            openDialog.value = true
                        }
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                confirmButton = {
                    Button(
                        onClick = { openDialog.value = false }) {
                        Text(text = "OK")
                    }
                },
                title = { Text("Как это работает?") },
                text = {
                    Text(
                        text = "Вы регистрируете заявку на обмен баллов на предмет магазина. " +
                                "Статус заявки можно отследить в профиле. " +
                                "После рассмотрения заявки Вам придёт необходимая информация через систему уведомлений. "
                    )
                }
            )
        }
    }
}
