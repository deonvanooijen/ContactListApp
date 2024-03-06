package com.deonvanooijen.contactlist.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Int,

    @ColumnInfo(name = "user_name")
    var name: String,

    @ColumnInfo(name = "user_phone_number")
    var phoneNumber: String
)