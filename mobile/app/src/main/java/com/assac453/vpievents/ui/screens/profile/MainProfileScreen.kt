package com.assac453.vpievents.ui.screens.profile

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.assac453.vpievents.LoginActivity
import com.assac453.vpievents.ui.graph.AppNavigation
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainProfileScreen(
    context: Context,
    navController: NavHostController,
    activity: ComponentActivity
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Профиль") }) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                /*.clickable {
                    Toast
                        .makeText(context, "Профиль", Toast.LENGTH_SHORT)
                        .show()
                }*/,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    ProfileCard(
                        text = "Уведомления",
                        onClick = { navController.navigate(AppNavigation.NOTIFICATION_ROUTE) }
                    )
                }
                item {
                    ProfileCard(
                        text = "Личная информация",
                        onClick = { showToast("Открыть окно изменения", context) }
                    )
                }
                item {
                    ProfileCard(
                        text = "Информация о школе",
                        onClick = { showToast("Открыть окно изменения", context) }
                    )
                }
                item {
                    ProfileCard(
                        text = "Дополнительная информация",
                        onClick = { showToast("Открыть окно изменения", context) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
                item {
                    Button(onClick = { logout(FirebaseAuth.getInstance(), navController, activity) }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Выйти из профиля")
                    }
                }
            }
        }

    }
}

fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun ProfileCard(text: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            fontSize = 18.sp
        )
    }
}


fun logout(auth: FirebaseAuth, navController: NavHostController, activity: ComponentActivity) {
    Toast.makeText(navController.context, "Вы вышли из профиля", Toast.LENGTH_SHORT).show()
    auth.signOut()
    val intent = Intent(navController.context, LoginActivity::class.java)
    navController.context.startActivity(intent)

    activity.finish()

}