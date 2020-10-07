package com.example.contactsinroomlibrary.recyclerviewadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsinroomlibrary.Contact
import com.example.contactsinroomlibrary.MainActivity
import com.example.contactsinroomlibrary.R
import com.example.contactsinroomlibrary.recyclerviewadapter.AdapterContacts.MyViewHolder
import java.util.*

class AdapterContacts(val context: Context,
                      private val contacts: ArrayList<Contact>,
                      private val mainActivity: MainActivity) : RecyclerView.Adapter<MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val nameTextView: TextView = view.findViewById(R.id.textViewName)
        internal val informationTextView: TextView = view.findViewById(R.id.textViewPhoneOrEmail)
        var imageViewPhotoContact: ImageView = view.findViewById(R.id.photoContact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_contact, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contact = contacts[position]
        holder.nameTextView.text = contact.name
        holder.informationTextView.text = contact.information
        holder.imageViewPhotoContact.setImageResource(contact.photo)
        holder.itemView.setOnClickListener { mainActivity.addAndEditContact(true, contact, position) }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

}