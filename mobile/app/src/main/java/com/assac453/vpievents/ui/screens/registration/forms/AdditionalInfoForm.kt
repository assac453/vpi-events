package com.assac453.vpievents.ui.screens.registration.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdditionalInfoForm(
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    telegramLink: String,
    onTelegramLinkChanged: (String) -> Unit,
    vkLink: String,
    onVkLinkChanged: (String) -> Unit,
    hasDisability: Boolean,
    onHasDisabilityChanged: (Boolean) -> Unit,
    isOrphan: Boolean,
    onIsOrphanChanged: (Boolean) -> Unit,
    registrationAddress: String,
    onRegistrationAddressChanged: (String) -> Unit,
    residentialAddress: String,
    onResidentialAddressChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Дополнительная информация",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = telegramLink,
            onValueChange = { onTelegramLinkChanged(it) },
            label = { Text("Ссылка на Telegram") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = vkLink,
            onValueChange = { onVkLinkChanged(it) },
            label = { Text("Ссылка на ВКонтакте") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = hasDisability,
                onCheckedChange = { onHasDisabilityChanged(it) },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Инвалидность",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isOrphan,
                onCheckedChange = { onIsOrphanChanged(it) },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Сирота",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registrationAddress,
            onValueChange = { onRegistrationAddressChanged(it) },
            label = { Text("Адрес прописки") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = residentialAddress,
            onValueChange = { onResidentialAddressChanged(it) },
            label = { Text("Фактический адрес проживания") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBackClicked) {
                Text("Назад")
            }
            Button(onClick = onNextClicked) {
                Text("Зарегистрироваться")
            }
        }
    }
}
