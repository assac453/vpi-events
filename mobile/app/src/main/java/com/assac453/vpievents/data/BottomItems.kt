package com.assac453.vpievents.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.assac453.vpievents.ui.graph.AppNavigation

data class BottomItems (val icon: ImageVector, val showName: String, val name: String)

fun getBottomItems(): List<BottomItems>{
    return listOf<BottomItems>(
        BottomItems(Icons.Rounded.ShoppingCart, "Магазин", AppNavigation.SHOP_ROUTE),
        BottomItems(Icons.Rounded.Place, "Мероприятия", AppNavigation.QRCODE_ROUTE),
        BottomItems(Icons.Rounded.AccountCircle, "Профиль", AppNavigation.PROFILE_ROUTE),
    )
}