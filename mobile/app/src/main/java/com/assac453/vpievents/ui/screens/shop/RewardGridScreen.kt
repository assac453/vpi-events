package com.assac453.vpievents.ui.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.assac453.vpievents.R
import com.assac453.vpievents.data.entity.Reward
import com.assac453.vpievents.ui.graph.AppNavigation

@Composable
fun RewardCard(reward: Reward, modifier: Modifier = Modifier, navController: NavHostController) {

    val textStyle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        shadow = Shadow(
            color = Color.Black,
            offset = Offset(2f, 2f),
            blurRadius = 4f
        )
    )

    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxSize()
            .requiredHeight(300.dp)
            .clickable { navigateToRewardtDetail(navController, reward) }
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            // Расположение фона карточки
            reward.url?.let {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = it)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                placeholder(R.drawable.loading_img)
                                error(R.drawable.ic_connection_error)
                            }).build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(alpha = 0.2f)) // Затемнение с прозрачностью 50%
                        .clip(shape = RoundedCornerShape(8.dp))
                        .drawWithContent {
                            drawContent()
                            drawRoundRect(
                                color = Color.Black.copy(alpha = 0.2f),
                                topLeft = Offset.Zero,
                                size = androidx.compose.ui.geometry.Size(size.width, size.height),
                                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                            )
                        },
                    contentScale = ContentScale.Crop // Масштабируем изображение до его заполнения
                )
            }

            // Расположение стоимости товара и иконки подарка
            Box(
                contentAlignment = Alignment.TopEnd,

                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.White.copy(0.4f), shape = RoundedCornerShape(8.dp))

                ) {
                    Icon(
                        Icons.Rounded.Star,
                        contentDescription = "Gift Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .border(1.dp, Color.White, shape = CircleShape)
                            .padding(4.dp)

                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = reward.cost.toString(),
                        style = textStyle,
                        modifier = Modifier
                    )
                }
            }
            // Расположение названия товара
            Text(
                text = reward.name ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 12.0f
                    )
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
}


@Composable
fun RewardGridScreen(
    rewards: List<Reward>,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    LazyVerticalGrid(columns = GridCells.Adaptive(300.dp), contentPadding = PaddingValues(4.dp)) {
        itemsIndexed(rewards) { _, reward ->
            RewardCard(reward = reward, navController = navController)
        }
    }
}

fun navigateToRewardtDetail(navController: NavHostController, reward: Reward) {
    AppNavigation.RewardContainer.selectedReward = reward
    navController.navigate(AppNavigation.REWARD_DESCRIPTION)
}