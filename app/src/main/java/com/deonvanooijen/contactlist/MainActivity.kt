package com.deonvanooijen.contactlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.deonvanooijen.contactlist.databinding.ActivityMainBinding
import com.deonvanooijen.contactlist.room.Contact
import com.deonvanooijen.contactlist.room.ContactDatabase
import com.deonvanooijen.contactlist.room.ContactRepository
import com.deonvanooijen.contactlist.viewUI.RecyclerViewAdapter
import com.deonvanooijen.contactlist.viewmodel.ContactViewModel
import com.deonvanooijen.contactlist.viewmodel.ContactViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Room database
        val dao = ContactDatabase.getInstance(application).userDAO
        val repository = ContactRepository(dao)
        val factory = ContactViewModelFactory(repository)

        contactViewModel = ViewModelProvider(this, factory).get(ContactViewModel::class.java)

        binding.contactViewModel = contactViewModel

        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        DisplayContactList()
    }

    private fun DisplayContactList() {
        contactViewModel.contacts.observe(this, Observer {
            binding.recyclerView.adapter = RecyclerViewAdapter(
                it, { selectedItem: Contact -> listItemClicked(selectedItem) }
            )
        })
    }

    private fun listItemClicked(selectedItem: Contact) {
        Toast.makeText(this, "Selected name is ${selectedItem.name}", Toast.LENGTH_LONG).show()

        contactViewModel.initUpdateAndDelete(selectedItem)
    }
}