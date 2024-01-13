package cz.mendelu.pef.mystyleapp.ui.screens

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import cz.mendelu.pef.mystyleapp.architecture.BaseViewModel
import cz.mendelu.pef.mystyleapp.database.FirestoreDB
import org.koin.core.component.KoinComponent


class WelcomeViewModel(
    private val context: Context
    ) : BaseViewModel(), KoinComponent {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun loginWithEmailAndPassword(email: String, password: String) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val authResult = task.result
                        val user = authResult?.user
                        // Handle successful login

                    } else {
                        // Handle login error
                        Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            // Handle login exception
            Toast.makeText(context, "Login error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun registerWithEmailAndPassword(username: String, email: String, password: String) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val authResult = task.result
                        val user = authResult?.user
                        user?.let { firebaseUser ->
                            val profileUpdate = UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build()
                            firebaseUser.updateProfile(profileUpdate)
                                .addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        // Handle successful registration and username update
                                        FirestoreDB.checkUserExists(email) { exists ->
                                            if (exists) {
                                                Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show()
                                            } else {
                                                FirestoreDB.saveUser(username, email)
                                            }
                                        }
                                    } else {
                                        // Handle username update error
                                        Toast.makeText(context, "Username update failed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                    } else {
                        // Handle registration error
                        Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            // Handle registration exception
            Toast.makeText(context, "Registration error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}