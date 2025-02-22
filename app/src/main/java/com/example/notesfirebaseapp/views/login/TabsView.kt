package com.example.notesfirebaseapp.views.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.notesfirebaseapp.viewModels.LoginViewModel

@Composable
fun TabsView(loginViewModel: LoginViewModel ,navController: NavController) {

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Iniciar sesión", "Registrarse")

    Column {
        TabRow(
            selectedTabIndex = selectedTab,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab])
                )

            }) {

            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTab == index,
                    onClick = {
                        selectedTab = index
                    },
                    text = {
                        Text(text = title)
                    }
                )

            }


        }

        when (selectedTab) {
            0 -> LoginView(loginViewModel, navController)
            1 -> SignUpView(loginViewModel, navController)
        }
    }

}