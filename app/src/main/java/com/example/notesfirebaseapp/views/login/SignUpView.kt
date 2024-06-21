package com.example.notesfirebaseapp.views.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notesfirebaseapp.components.Alerta
import com.example.notesfirebaseapp.viewModels.LoginViewModel

@Composable
fun SignUpView(loginViewModel: LoginViewModel, navController: NavController) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }

        //Text Username
        OutlinedTextField(
            value = username,
            onValueChange = { value ->
                username = value
            },
            label = {
                Text(text = "Usuario")
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        //Text Email
        OutlinedTextField(
            value = email,
            onValueChange = { value ->
                email = value
            },
            label = {
                Text(text = "Email")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
        )

        //Text passwords
        OutlinedTextField(
            value = password,
            onValueChange = { value ->
                password = value
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                loginViewModel.createUser(email, password, username) {
                    navController.navigate("Home")
                }
            },
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(text = "Registrar")
        }

        if (loginViewModel.showAlert) {
            Alerta(
                titulo = "Error",
                mensaje = "Usuario no creado",
                confirmText = "Aceptar",
                onConfirmClick = { loginViewModel.closeAlert() }) {

            }
        }

    }

}