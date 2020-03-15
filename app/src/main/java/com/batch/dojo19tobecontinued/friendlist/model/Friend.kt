package com.batch.dojo19tobecontinued.friendlist.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["fullName", "githubID", "twitterID"])
data class Friend(
    @ColumnInfo(name = "fullName")
    val fullName: String,
    @ColumnInfo(name = "githubID")
    val githubID: String,
    @ColumnInfo(name = "twitterID")
    val twitterID: String
)