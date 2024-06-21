package com.example.notesfirebaseapp.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Alerta(
    titulo: String,
    mensaje: String,
    confirmText: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
) {
    val scroll = rememberScrollState(0)

    AlertDialog(
        onDismissRequest = { onDismissClick },
        title = {
            Text(text = titulo)
        },
        text = {
            Text(
                text = mensaje,
                textAlign = TextAlign.Justify,
                modifier = Modifier.verticalScroll(scroll)
            )
        },
        confirmButton = {
            Button(onClick = { onConfirmClick() }) {
                Text(text = confirmText)
            }
        }

    )


}