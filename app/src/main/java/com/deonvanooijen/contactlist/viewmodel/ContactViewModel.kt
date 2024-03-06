package com.deonvanooijen.contactlist.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deonvanooijen.contactlist.room.Contact
import com.deonvanooijen.contactlist.room.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository) : ViewModel(),
    androidx.databinding.Observable {

    val contacts = repository.contacts
    private var isUpdateOrDelete = false
    private lateinit var contactToUpdateOrDelete: Contact

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputPhoneNumber = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            // Make Update:
            contactToUpdateOrDelete.name = inputName.value!!
            contactToUpdateOrDelete.phoneNumber = inputPhoneNumber.value!!
            update(contactToUpdateOrDelete)
        } else {
            // Insert Functionality
            val name = inputName.value!!
            val phoneNumber = inputPhoneNumber.value!!

            insert(Contact(0, name, phoneNumber))

            inputName.value = null
            inputPhoneNumber.value = null
        }


    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(contactToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    private fun update(contact: Contact) = viewModelScope.launch {
        repository.update(contact)

        // Resetting the buttons and fields
        inputName.value = null
        inputPhoneNumber.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }

    fun delete(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)

        // Resetting the buttons and fields
        inputName.value = null
        inputPhoneNumber.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun initUpdateAndDelete(contact: Contact) {
        // Resetting the buttons and fields
        inputName.value = contact.name
        inputPhoneNumber.value = contact.phoneNumber
        isUpdateOrDelete = true
        contactToUpdateOrDelete = contact
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}