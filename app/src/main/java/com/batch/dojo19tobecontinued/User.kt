package com.batch.dojo19tobecontinued

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["fullName", "gitHub", "twitter"])
data class User (

    //@PrimaryKey(autoGenerate = true)
    //var uid: Int = 0,

    @ColumnInfo(name = "fullName")
    var fullName: String = "" ,

    @ColumnInfo(name = "gitHub")
    var gitHub: String = "",

    @ColumnInfo(name = "twitter")
    var twitter: String = ""
)