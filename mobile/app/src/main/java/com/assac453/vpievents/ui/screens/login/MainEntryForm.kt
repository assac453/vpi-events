package com.assac453.vpievents.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.assac453.vpievents.R
import com.assac453.vpievents.ui.graph.AppNavigation

@Composable
fun MainEntryForm(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp,),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "ВПИ События", fontWeight = FontWeight.Bold, fontSize = 30.sp)

        Spacer(modifier = Modifier.height(64.dp))
        Icon(painter = painterResource(id = R.drawable.vpi_logo), contentDescription = "VPI logo", modifier= Modifier
            .height(128.dp)
            .width(128.dp))
        Spacer(modifier = Modifier.height(64.dp))

        Button(onClick = { navController.navigate(AppNavigation.REGISTER_MAIN_ROUTE) }, modifier = Modifier.fillMaxWidth() ) {
            Text(text = "Зарегистрироваться")
        }
        Button(onClick = { navController.navigate(AppNavigation.AUTH_ROUTE) }, modifier = Modifier.fillMaxWidth() ) {
            Text(text = "Войти")
        }
    }
}