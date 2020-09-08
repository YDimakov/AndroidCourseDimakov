package com.example.contactsinroomlibrary

import Data.ContactAppDatabase
import RecyclerViewAdapter.AdapterContacts
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import java.util.*

class MainActivity : AppCompatActivity() {
    private var id: Long = 0
    private lateinit var adapterContacts: AdapterContacts
    private val arrayListContacts = ArrayList<Contact?>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageButton: ImageButton
    private lateinit var nameEditText: EditText
    private lateinit var newContactTitle: TextView
    private lateinit var informationEditText: EditText
    private var alertDialog: AlertDialog? = null
    private lateinit var view: View
    private var alertDialogBuilderUserInput: AlertDialog.Builder? = null
    private lateinit var layoutInflaterAndroid: LayoutInflater
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var contact: Contact? = null
    private lateinit var contactAppDatabase: ContactAppDatabase
    private lateinit var checkBoxPhone: CheckBox
    private lateinit var checkBoxEmail: CheckBox
    private lateinit var imageViewPhoto: ImageView
    private var checkedCheckBox = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        contactAppDatabase = Room.databaseBuilder(applicationContext, ContactAppDatabase::class.java,"DB").allowMainThreadQueries().build()
        contactAppDatabase.contactDAO.allContacts.let { arrayListContacts.addAll(it) }
        adapterContacts = AdapterContacts(this, arrayListContacts, this@MainActivity)
        mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapterContacts
        imageViewPhoto = findViewById(R.id.photoContact)
        imageButton = findViewById(R.id.imageButtonAdd)
        imageButton.setOnClickListener { addAndEditContact(false, null, -1) }
    }

    fun addAndEditContact(isUpdate: Boolean, contact: Contact?, position: Int) {
        layoutInflaterAndroid = LayoutInflater.from(applicationContext)
        view = layoutInflaterAndroid.inflate(R.layout.activity_add_contacts, null)
        alertDialogBuilderUserInput = AlertDialog.Builder(this@MainActivity)
        alertDialogBuilderUserInput!!.setView(view)
        newContactTitle = view.findViewById(R.id.newContactTitle)
        nameEditText = view.findViewById(R.id.nameEditText)
        checkBoxPhone = view.findViewById(R.id.checkBoxPhone)
        checkBoxEmail = view.findViewById(R.id.checkBoxEmail)
        informationEditText = view.findViewById(R.id.informationEditText)
        checkingTwoParameters()
        newContactTitle.text = if (!isUpdate) "Добавить контакт" else "Изменение контакта"
        if (isUpdate && contact != null) {
            nameEditText.run { setText(contact.name) }
            informationEditText.setText(contact.information)
        }
        alertDialogBuilderUserInput!!
                .setCancelable(false)
                .setPositiveButton(if (isUpdate) "Обновить" else "Сохранить") { _, _ -> }
                .setNegativeButton(if (isUpdate) "Удалить" else "Отмена") { dialogBox, _ ->
                    if (isUpdate) {
                        deleteContact(contact, position)
                    } else {
                        dialogBox.cancel()
                    }
                }
        alertDialog = alertDialogBuilderUserInput!!.create()
        alertDialog!!.show()
        alertDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(View.OnClickListener {
            when {
                TextUtils.isEmpty(nameEditText.text.toString()) -> {
                    Toast.makeText(this@MainActivity, "Введите имя!", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                TextUtils.isEmpty(informationEditText.text.toString()) -> {
                    Toast.makeText(this@MainActivity, "Введите информацию о пользователе", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                else -> {
                    alertDialog!!.dismiss()
                }
            }
            if (isUpdate && contact != null) {
                updateContact(nameEditText.text.toString(), informationEditText.text.toString(), position)
            } else {
                createContact(nameEditText.text.toString(), informationEditText.text.toString())
            }
        })
    }

    private fun checkingTwoParameters() {
        checkBoxEmail!!.setOnClickListener {
            if (checkBoxEmail!!.isChecked) {
                checkedCheckBox = true
                Toast.makeText(this@MainActivity, "Введите свою почту!", Toast.LENGTH_SHORT).show()
            }
        }
        checkBoxPhone!!.setOnClickListener {
            if (checkBoxPhone!!.isChecked) {
                checkedCheckBox = false
                Toast.makeText(this@MainActivity, "Введите свой телефон", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteContact(contact: Contact?, position: Int) {
        arrayListContacts.removeAt(position)
        contactAppDatabase!!.contactDAO!!.deleteContact(contact!!)
        adapterContacts!!.notifyDataSetChanged()
    }

    private fun updateContact(name: String, info: String, position: Int) {
        contact = arrayListContacts[position]
        contact!!.name = name
        contact!!.information = info
        if (checkedCheckBox) {
            contact!!.photo = R.drawable.ic_baseline_email_turquoise
        } else {
            contact!!.photo = R.drawable.ic_baseline_contact_phone_purple
        }
        contactAppDatabase!!.contactDAO!!.updateContact(contact!!)
        arrayListContacts[position] = contact
        adapterContacts!!.notifyDataSetChanged()
    }

    private fun createContact(name: String, info: String) {
        id = if (checkedCheckBox) {
            contactAppDatabase!!.contactDAO!!.addContact(Contact(0, name, info, R.drawable.ic_baseline_email_turquoise))
        } else {
            contactAppDatabase!!.contactDAO!!.addContact(Contact(0, name, info, R.drawable.ic_baseline_contact_phone_purple))
        }
        contact = contactAppDatabase!!.contactDAO!!.getContact(id)
        if (contact != null) {
            arrayListContacts.add(0, contact)
            adapterContacts!!.notifyDataSetChanged()
        }
    }
}