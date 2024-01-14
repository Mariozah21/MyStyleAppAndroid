package cz.mendelu.pef.mystyleapp.firestoreCloudStorage

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreDB {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun saveUser(username: String, email: String) {
        val user = hashMapOf(
            "username" to username,
            "email" to email
        )
        firestore.collection("users")
            .document(email)
            .set(user)
            .addOnSuccessListener {
                // User data saved successfully
            }
            .addOnFailureListener { e ->
                // Error saving user data
                // Handle the error appropriately (e.g., show a Toast)
            }
    }

    fun checkUserExists(email: String, onSuccess: (Boolean) -> Unit) {
        firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                val exists = !documents.isEmpty
                onSuccess(exists)
            }
            .addOnFailureListener { e ->
                // Error checking user existence
                // Handle the error appropriately (e.g., show a Toast)
            }
    }

    // Add more Firestore functions here as needed
}