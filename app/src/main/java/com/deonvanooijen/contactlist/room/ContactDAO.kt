package com.deonvanooijen.contactlist.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDAO {

    @Insert
    suspend fun insertContact(contact: Contact): Long

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("DELETE FROM contact")
    suspend fun deleteAll(): Unit

    @Query("SELECT * FROM contact")
    fun getAllContactsFromDB(): LiveData<List<Contact>>
}