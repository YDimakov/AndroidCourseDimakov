package com.example.contactsinroomlibrary

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.contactsinroomlibrary.data.ContactAppDatabase
import com.example.contactsinroomlibrary.recyclerviewadapter.AdapterContacts
import kotlinx.android.synthetic.main.activity_add_contacts.view.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var id: Long = 0
    private lateinit var adapterContacts: AdapterContacts
    private val arrayListContacts = ArrayList<Contact>()
    private lateinit var view: View
    private lateinit var contact: Contact
    private lateinit var contactAppDatabase: ContactAppDatabase
    private var checkedCheckBox = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        contactAppDatabase = Room.databaseBuilder(applicationContext, ContactAppDatabase::class.java, "DB").allowMainThreadQueries().build()
        contactAppDatabase.getContactDAO().getAllContacts().let { arrayListContacts.addAll(it) }
        adapterContacts = AdapterContacts(this, arrayListContacts, this@MainActivity)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapterContacts
        imageButtonAdd.setOnClickListener { addAndEditContact(false, null, -1) }
    }

    fun addAndEditContact(isUpdate: Boolean, contact: Contact?, position: Int) { // private не ставлю так как вызываю эту функцию в AdapterContact
        val layoutInflaterAndroid = LayoutInflater.from(applicationContext)
        view = layoutInflaterAndroid.inflate(R.layout.activity_add_contacts, null)
        val alertDialogBuilderUserInput = AlertDialog.Builder(this@MainActivity)
        alertDialogBuilderUserInput.setView(view)
        checkingTwoParameters()
        view.newContactTitle.text = if (!isUpdate) "Добавить контакт" else "Изменение контакта"
        if (isUpdate && contact != null) {
            view.nameEditText.run { setText(contact.name) }
            view.informationEditText.setText(contact.information)
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(if (isUpdate) "Обновить" else "Сохранить") { _, _ -> }
                .setNegativeButton(if (isUpdate) "Удалить" else "Отмена") { dialogBox, _ ->
                    if (isUpdate) {
                        deleteContact(contact, position)
                    } else {
                        dialogBox.cancel()
                    }
                }
        val alertDialog = alertDialogBuilderUserInput.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(View.OnClickListener {
            when {
                TextUtils.isEmpty(view.nameEditText.text.toString()) -> {
                    Toast.makeText(this@MainActivity, "Введите имя!", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                TextUtils.isEmpty(view.informationEditText.text.toString()) -> {
                    Toast.makeText(this@MainActivity, "Введите информацию о пользователе", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                else -> {
                    alertDialog.dismiss()
                }
            }
            if (isUpdate && contact != null) {
                updateContact(view.nameEditText.text.toString(), view.informationEditText.text.toString(), position)
            } else {
                createContact(view.nameEditText.text.toString(), view.informationEditText.text.toString())
            }
        })
    }

    private fun checkingTwoParameters() {
        view.checkBoxEmail.setOnClickListener {
            if (view.checkBoxEmail.isChecked) {
                checkedCheckBox = true
                Toast.makeText(this@MainActivity, "Введите свою почту!", Toast.LENGTH_SHORT).show()
            }
        }
        view.checkBoxPhone.setOnClickListener {
            if (view.checkBoxPhone.isChecked) {
                checkedCheckBox = false
                Toast.makeText(this@MainActivity, "Введите свой телефон", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteContact(contact: Contact?, position: Int) {
        arrayListContacts.removeAt(position)
        contact?.let { contactAppDatabase.getContactDAO().deleteContact(it) }
        adapterContacts.notifyDataSetChanged()
    }

    private fun updateContact(name: String, info: String, position: Int) {
        contact = arrayListContacts[position]
        contact.name = name
        contact.information = info
        if (checkedCheckBox) {
            contact.photo = R.drawable.ic_baseline_email_turquoise
        } else {
            contact.photo = R.drawable.ic_baseline_contact_phone_purple
        }
        contactAppDatabase.getContactDAO().updateContact(contact)
        arrayListContacts[position] = contact
        adapterContacts.notifyDataSetChanged()
    }

    private fun createContact(name: String, info: String) {
        id = if (checkedCheckBox) {
            contactAppDatabase.getContactDAO().addContact(Contact(0, name, info, R.drawable.ic_baseline_email_turquoise))
        } else {
            contactAppDatabase.getContactDAO().addContact(Contact(0, name, info, R.drawable.ic_baseline_contact_phone_purple))
        }
        contact = contactAppDatabase.getContactDAO().getContact(id)
        arrayListContacts.add(0, contact)
        adapterContacts.notifyDataSetChanged()
    }
}