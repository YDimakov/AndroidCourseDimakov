package com.example.contactswiththread.InterfaceDataProcessing;

import com.example.contactswiththread.Contact;
import com.example.contactswiththread.Data.ContactAppDatabase;

import java.util.ArrayList;

public interface WorkWithTread {

    void deleteContact(Contact contact, int position, ArrayList<Contact> arrayListContacts, ContactAppDatabase contactAppDatabase);

    void updateContact(Contact contact, ArrayList<Contact> arrayListContacts, boolean checkedCheckBox,
                       ContactAppDatabase contactAppDatabase, String name, String info, int position);

    void createContact(Contact contact, boolean checkedCheckBox, ContactAppDatabase contactAppDatabase,
                       ArrayList<Contact> arrayListContacts, String name, String info);
}
