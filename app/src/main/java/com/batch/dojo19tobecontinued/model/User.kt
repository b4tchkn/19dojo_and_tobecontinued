package com.batch.dojo19tobecontinued.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["fullName", "gitHub", "twitter"])
data class User (

    @ColumnInfo(name = "fullName")
    var fullName: String = "" ,

    @ColumnInfo(name = "gitHub")
    var gitHub: String = "",

    @ColumnInfo(name = "twitter")
    var twitter: String = ""
)