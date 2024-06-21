package com.example.notesfirebaseapp.views.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.notesfirebaseapp.components.CardNote
import com.example.notesfirebaseapp.viewModels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, notesViewModel: NotesViewModel){

    LaunchedEffect(Unit) {
        notesViewModel.getAllNotes()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Notas")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        notesViewModel.signOut()
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("AddNote")
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "")
                    }
                }
            )
        }

    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally) {

            val listNotas by notesViewModel.listNotes.collectAsState()
            LazyColumn {
                items(listNotas){ nota -> 
                    CardNote(tituloNota = nota.title, descripcionNota = nota.description, fechaNota = nota.date) {
                        navController.navigate("EditNote/${nota.idDocumento}")
                    }
                    
                }
            }


        }

    }
}