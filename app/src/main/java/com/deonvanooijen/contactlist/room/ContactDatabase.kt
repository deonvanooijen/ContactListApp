package com.deonvanooijen.contactlist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract val userDAO: ContactDAO

    // Singleton Design Pattern
    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null
        fun getInstance(context: Context): ContactDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    // Creating the database object
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "contact_db"
                    ).build()
                }
                return instance
            }
        }
    }

}