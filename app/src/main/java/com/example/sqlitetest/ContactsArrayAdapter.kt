package com.example.sqlitetest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ContactsArrayAdapter(private val context: Context,private val resource:Int,private val contactsList:ArrayList<Contacts>):ArrayAdapter<Contacts>(context,resource) {
    override fun getCount(): Int {
        return contactsList.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resource,parent,false)
      val name = view.findViewById<TextView>(R.id.contact_name)
      val phone = view.findViewById<TextView>(R.id.contact_phone)
      val email = view.findViewById<TextView>(R.id.contact_email)
        name.text = contactsList[position].name
        phone.text = contactsList[position].phone.toString()
        email.text = contactsList[position].email
        return view
    }
}