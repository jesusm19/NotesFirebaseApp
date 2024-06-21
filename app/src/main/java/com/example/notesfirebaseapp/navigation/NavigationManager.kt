package com.example.notesfirebaseapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesfirebaseapp.viewModels.LoginViewModel
import com.example.notesfirebaseapp.viewModels.NotesViewModel
import com.example.notesfirebaseapp.views.login.BlankView
import com.example.notesfirebaseapp.views.login.TabsView
import com.example.notesfirebaseapp.views.notes.AddNoteView
import com.example.notesfirebaseapp.views.notes.EditNoteView
import com.example.notesfirebaseapp.views.notes.HomeView

@Composable
fun NavigationManager(loginViewModel: LoginViewModel, notesViewModel: NotesViewModel){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Blank") {
        composable("Blank") {
            BlankView(navController)
        }
        composable("Login") {
            TabsView(loginViewModel, navController)
        }
        composable("Home") {
            HomeView(navController, notesViewModel)
        }
        composable("AddNote") {
            AddNoteView(navController, notesViewModel)
        }
        composable("EditNote/{idDocumento}",
            arguments = listOf(
                navArgument("idDocumento"){
                    type = NavType.StringType
                }
            )) {
            val idDocumento = it.arguments?.getString("idDocumento") ?: ""
            EditNoteView(navController, notesViewModel, idDocumento)
        }
    }

}