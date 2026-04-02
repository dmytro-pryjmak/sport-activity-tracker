package com.work.activitytracker

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.work.activitytracker.navigation.NavGraph

private sealed interface AuthState {
    data class Offline(val reason: String) : AuthState
    data object Loading : AuthState
    data object Ready : AuthState
}

@Composable
internal fun AuthGate(firebaseAuth: FirebaseAuth) {
    var authState by remember { mutableStateOf<AuthState>(AuthState.Loading) }

    LaunchedEffect(Unit) {
        if (firebaseAuth.currentUser != null) {
            Log.d(TAG, "User already signed in: ${firebaseAuth.currentUser?.uid}")
            authState = AuthState.Ready
        } else {
            Log.d(TAG, "Signing in anonymously…")
            firebaseAuth.signInAnonymously()
                .addOnSuccessListener { result ->
                    Log.d(TAG, "Sign-in success uid=${result.user?.uid}")
                    authState = AuthState.Ready
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Sign-in failed — offline mode", e)
                    authState = AuthState.Offline(e.message ?: "No network")
                }
        }
    }

    when (authState) {
        AuthState.Loading -> SplashContent()
        AuthState.Ready -> AppContent()
        is AuthState.Offline -> AppContent(offline = true)
    }
}

@Composable
private fun AppContent(offline: Boolean = false) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(offline) {
        if (offline) {
            snackbarHostState.showSnackbar(
                message = "Offline — only local storage available",
                withDismissAction = true,
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavGraph(navController = navController)
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun SplashContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.DirectionsRun,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(Modifier.height(24.dp))
            CircularProgressIndicator(modifier = Modifier.size(28.dp), strokeWidth = 2.dp)
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.signing_in),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

private const val TAG = "SportTracker"
