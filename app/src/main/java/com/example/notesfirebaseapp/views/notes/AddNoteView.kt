package com.example.notesfirebaseapp.views.notes

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notesfirebaseapp.viewModels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteView(navController: NavController, notesViewModel: NotesViewModel) {

    var tituloNota by remember { mutableStateOf("") }
    var descripcionNota by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Nueva nota")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Log.d("Entra", "Entra ")
                        notesViewModel.saveNewNote(tituloNota, descripcionNota){
                            Toast.makeText(context, "Se guardó correctamente la nota", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.AddCircle, contentDescription = "")
                    }
                }
            )
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            OutlinedTextField(
                value = tituloNota,
                onValueChange = { value ->
                    tituloNota = value
                },
                label = {
                    Text(text = "Titulo")
                },
                modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp)
            )

            OutlinedTextField(
                value = descripcionNota,
                onValueChange = { value ->
                    descripcionNota = value
                },
                label = {
                    Text(text = "Descripción")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(20.dp)
            )

        }

    }

}
