package com.work.activitytracker.core.data.remote.source

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class FirestoreRecordSource @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    fun observeRecords(userId: String): Flow<List<Map<String, Any>>> = callbackFlow {
        Log.d(TAG, "Starting Firestore listener for userId=$userId")
        val registration = userRecordsCollection(userId)
            .orderBy(FIELD_CREATED_AT, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Firestore snapshot error", error)
                    close(error)
                    return@addSnapshotListener
                }
                val records = snapshot?.documents
                    ?.mapNotNull { doc -> doc.data?.plus(FIELD_ID to doc.id) }
                    ?: emptyList()
                Log.d(TAG, "Firestore snapshot received: ${records.size} remote records")
                trySend(records)
            }
        awaitClose {
            Log.d(TAG, "Removing Firestore listener for userId=$userId")
            registration.remove()
        }
    }

    fun save(userId: String, recordId: String, data: Map<String, Any>) {
        Log.d(TAG, "Saving record $recordId for userId=$userId")
        userRecordsCollection(userId).document(recordId).set(data)
            .addOnSuccessListener { Log.d(TAG, "Record $recordId synced to server") }
            .addOnFailureListener { e -> Log.e(TAG, "Record $recordId server sync failed", e) }
    }

    suspend fun delete(userId: String, recordId: String) {
        Log.d(TAG, "Deleting record $recordId for userId=$userId")
        userRecordsCollection(userId).document(recordId).delete().await()
        Log.d(TAG, "Record $recordId deleted successfully")
    }

    private fun userRecordsCollection(userId: String) =
        firestore.collection(COLLECTION_USERS).document(userId).collection(COLLECTION_RECORDS)

    companion object {
        private const val TAG = "FirestoreSource"
        private const val COLLECTION_USERS = "users"
        private const val COLLECTION_RECORDS = "records"
        const val FIELD_ID = "id"
        const val FIELD_CREATED_AT = "createdAt"
    }
}
