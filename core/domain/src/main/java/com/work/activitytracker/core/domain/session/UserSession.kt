package com.work.activitytracker.core.domain.session

interface UserSession {
    // UID of the current signed-in user, or empty string if not authenticated yet
    val currentUserId: String
}
