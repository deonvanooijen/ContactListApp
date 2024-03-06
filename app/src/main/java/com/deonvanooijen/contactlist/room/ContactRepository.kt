package com.deonvanooijen.contactlist.room

class ContactRepository(private val dao: ContactDAO) {

    val contacts = dao.getAllContactsFromDB()

    suspend fun insert(contact: Contact): Long {
        return dao.insertContact(contact)
    }

    suspend fun delete(contact: Contact) {
        return dao.deleteContact(contact)
    }

    suspend fun update(contact: Contact) {
        return dao.updateContact(contact)
    }

    suspend fun deleteAll() {
        return dao.deleteAll()
    }
}