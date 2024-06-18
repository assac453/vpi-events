package com.assac453.vpievents.ui.screens.main

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.assac453.vpievents.R
import com.assac453.vpievents.ui.graph.AppNavigation
import com.assac453.vpievents.ui.screens.main.helper.CallAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeScreen(
    scanLauncher: (context: Context) -> Unit,
    textResult: MutableState<String>,
    context: Context,
    navController: NavHostController
) {

    val openDialog = remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Сканировать QR-код") }) }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            Text(text = "За участие в мероприятиях будут начисленны баллы. Воспользуйтесь кнопкой \"Быстрое сканирование\" для регистрации на мероприятие")
            Spacer(modifier = Modifier.size(30.dp))
            Text(text = "Как это работает?",
                style = TextStyle(textDecoration = TextDecoration.Underline, color = Color.Blue),
                modifier = Modifier.clickable {
                    openDialog.value = true
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
                Button(
                    onClick = { scanLauncher(context) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                ) {
                    Text(text = "Быстрое сканирование") //modifier = Modifier.padding(paddingValues))
                    Icon(
                        painter = painterResource(id = R.drawable.qr_scan),
                        contentDescription = "QR scan",
//                        modifier = Modifier.size(20.dp)
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(AppNavigation.CALENDAR_ROUTE)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Перейти к мероприятиям")
                    Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "toEvents")
                }

            }
            Text(text = textResult.value, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }

        CallAlertDialog(openDialog = openDialog.value, onOpenDialogChanged = {openDialog.value = it}, title = "Вы зарегистрировались на мероприятии", message = "Вам будут начисленны баллы за посещение мероприятия")
    }
}
