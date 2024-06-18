package com.assac453.vpievents.ui.screens.main.helper

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun CallAlertDialog(
    openDialog: Boolean,
    onOpenDialogChanged: (Boolean) -> Unit,
    title: String,
    message: String,
    confirmButtonText: String = "OK",
    onConfirmButtonClick: () -> Unit = {}
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { onOpenDialogChanged(false) },
            confirmButton = {
                Button(
                    onClick = {
                        onOpenDialogChanged(false)
                        onConfirmButtonClick()
                    }
                ) {
                    Text(text = confirmButtonText)
                }
            },
            title = { Text(title) },
            text = { Text(message) }
        )
    }
}
