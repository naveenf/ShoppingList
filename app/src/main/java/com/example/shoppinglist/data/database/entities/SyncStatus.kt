package com.example.shoppinglist.data.database.entities

enum class SyncStatus {
    LOCAL_ONLY,    // Item exists only locally, no family sync
    PENDING,       // Local changes waiting to be synced to cloud
    SYNCED,        // Item is synced with cloud and up-to-date
    CONFLICT       // Item has conflicts that need manual resolution
}