package com.work.activitytracker.core.data.session

import com.google.firebase.auth.FirebaseAuth
import com.work.activitytracker.core.domain.session.UserSession
import javax.inject.Inject

internal class FirebaseUserSession @Inject constructor(
    private val auth: FirebaseAuth,
) : UserSession {
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()
}
