package com.example.notesfirebaseapp.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesfirebaseapp.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    //Se agrega una referencia a firebase
    private val auth: FirebaseAuth = Firebase.auth
    var showAlert by mutableStateOf(false)




    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                //Seleccionamos el medio de login
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful()){
                            onSuccess()

                        } else {
                            Log.d("Error en Firebase", "Usuario y/o contraseña incorrecta")
                            showAlert = true
                        }

                    }

            } catch(e: Exception){
                Log.d("Error en Jetpack", "Error: ${e.localizedMessage}")

            }

        }
    }

    fun createUser(email: String, password: String, username: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                //Seleccionamos el medio de login
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful()){
                            saveUser(username)
                            onSuccess()

                        } else {
                            Log.d("Error en Firebase", "Error al crear usuario")
                            showAlert = true
                        }

                    }

            } catch(e: Exception){
                Log.d("Error en Jetpack", "Error: ${e.localizedMessage}")

            }

        }
    }

    private fun saveUser(username: String){
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val user = UserModel(id.toString(), email.toString(), username)

            FirebaseFirestore.getInstance().collection("Users")
                .add(user)
                .addOnSuccessListener {
                    Log.d("Guardado", "Guardadd correctamente")
                }
                .addOnFailureListener {
                    Log.d("Error en Firebase", "Error al crear usuario en firestore")
                }

        }

    }

    fun closeAlert () {
        showAlert = false
    }

}