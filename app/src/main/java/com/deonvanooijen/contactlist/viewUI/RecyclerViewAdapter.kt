package com.deonvanooijen.contactlist.viewUI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.deonvanooijen.contactlist.R
import com.deonvanooijen.contactlist.databinding.ContactItemBinding
import com.deonvanooijen.contactlist.room.Contact

class RecyclerViewAdapter(
    private val contactList: List<Contact>,
    private val clickListener: (Contact) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ContactItemBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.contact_item, parent, false
        )

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactList[position], clickListener)
    }

}

class MyViewHolder(val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(contact: Contact, clickListener: (Contact) -> Unit) {
        binding.textViewName.text = contact.name
        binding.textViewPhone.text = contact.phoneNumber

        binding.listItemLayout.setOnClickListener {
            clickListener(contact)
        }
    }

}