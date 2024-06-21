package com.example.notesfirebaseapp.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesfirebaseapp.NoteState
import com.example.notesfirebaseapp.models.NoteModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NotesViewModel: ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val _notesState = MutableStateFlow<List<NoteState>>(emptyList())
    val listNotes: StateFlow<List<NoteState>> = _notesState

    var noteState by mutableStateOf(NoteState())
        private set

    fun signOut() {
        auth.signOut()
    }

    fun onValueTitle(value: String) {
        noteState = noteState.copy(title = value)
    }

    fun onValueDescription(value: String) {
        noteState = noteState.copy(description = value)
    }

    fun saveNewNote(tituloNota: String, descripcionNota: String, onSuccess: () -> Unit){
        val email = auth.currentUser?.email
        val fecha = formatDate()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newNote = NoteModel(tituloNota, descripcionNota, fecha, email.toString())
                firestore.collection("Notes").add(newNote)
                    .addOnSuccessListener {
                        onSuccess()
                    }

            } catch (e: Exception){
                Log.d("Error al guardar", "Error al guardardar: ${e.localizedMessage}")
            }
        }


    }

    private fun formatDate(): String {
        val currentDate: Date = Calendar.getInstance().time
        val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return fecha.format(currentDate)
    }

    fun getAllNotes(){
        val email = auth.currentUser?.email
        firestore.collection("Notes")
            .whereEqualTo("email", email.toString())
            .addSnapshotListener { querySnapshot, error ->
                if(error != null) {
                    return@addSnapshotListener
                }
                val documents = mutableListOf<NoteState>()
                if(querySnapshot != null) {
                    for (document in querySnapshot){
                        val myDocument = document.toObject(NoteState::class.java).copy(idDocumento = document.id)
                        documents.add(myDocument)
                    }
                }
                _notesState.value = documents

            }
    }

    fun getNoteById(idDocumento: String){
        firestore.collection("Notes")
            .document(idDocumento)
            .addSnapshotListener { snapshot, error ->
                if (snapshot  != null) {
                    val note = snapshot.toObject(NoteState::class.java)
                    noteState = noteState.copy(
                        title = note?.title ?: "",
                        description = note?.description ?: ""
                    )
                }

            }
    }

    fun editNote(idDocumento: String, onSuccess: () -> Unit){

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val note = hashMapOf(
                    "title" to noteState.title,
                    "description" to noteState.description
                )
                firestore.collection("Notes").document(idDocumento)
                    .update(note as Map<String, Any>)
                    .addOnSuccessListener {
                        onSuccess()
                    }

            } catch (e: Exception){
                Log.d("Error al editar", "Error al Editar: ${e.localizedMessage}")
            }
        }


    }

    fun deleteNote(idDocumento: String, onSuccess: () -> Unit) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                firestore.collection("Notes").document(idDocumento)
                    .delete()
                    .addOnSuccessListener {
                        onSuccess()
                    }

            } catch (e: Exception) {
                Log.d("Error al eliminar", "Error al Eliminar: ${e.localizedMessage}")
            }
        }
    }

}