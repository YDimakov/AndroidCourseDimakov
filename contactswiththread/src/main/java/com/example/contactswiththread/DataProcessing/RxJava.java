package com.example.contactswiththread.DataProcessing;


import com.example.contactswiththread.Contact;
import com.example.contactswiththread.Data.ContactAppDatabase;
import com.example.contactswiththread.InterfaceDataProcessing.WorkWithTread;
import com.example.contactswiththread.R;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class RxJava implements WorkWithTread {
    private long id;

    @Override
    public void deleteContact(final Contact contact, final int position,
                              final ArrayList<Contact> arrayListContacts,
                              final ContactAppDatabase contactAppDatabase) {

        Observable.create(new ObservableOnSubscribe<Contact>() {
            @Override
            public void subscribe(ObservableEmitter<Contact> emitter) {
                arrayListContacts.remove(position);
                contactAppDatabase.getContactDAO().deleteContact(contact);
            }
        }).subscribe();
    }

    @Override
    public void updateContact(Contact contact, final ArrayList<Contact> arrayListContacts, final boolean checkedCheckBox, final ContactAppDatabase contactAppDatabase, final String name, final String info, final int position) {
        contact = arrayListContacts.get(position);
        final Contact finalContact = contact;
        Observable.create(new ObservableOnSubscribe<Contact>() {
            @Override
            public void subscribe(ObservableEmitter<Contact> emitter) {
                finalContact.setName(name);
                finalContact.setInformation(info);
                if (checkedCheckBox) {
                    finalContact.setPhoto(R.drawable.ic_baseline_email_turquoise);
                } else {
                    finalContact.setPhoto(R.drawable.ic_baseline_contact_phone_purple);
                }
                contactAppDatabase.getContactDAO().updateContact(finalContact);
                arrayListContacts.set(position, finalContact);
            }
        }).subscribe();
    }

    @Override
    public void createContact(Contact contact, final boolean checkedCheckBox,
                              final ContactAppDatabase contactAppDatabase,
                              final ArrayList<Contact> arrayListContacts,
                              final String name, final String info) {
        contact = contactAppDatabase.getContactDAO().getContact(id);
        final Contact finalContact = contact;
        Observable.create(new ObservableOnSubscribe<Contact>() {
            @Override
            public void subscribe(ObservableEmitter<Contact> emitter) {
                if (checkedCheckBox) {
                    id = contactAppDatabase.getContactDAO().addContact(new Contact(0, name, info, R.drawable.ic_baseline_email_turquoise));
                } else {
                    id = contactAppDatabase.getContactDAO().addContact(new Contact(0, name, info, R.drawable.ic_baseline_contact_phone_purple));
                }
                if (finalContact != null) {
                    arrayListContacts.add(0, finalContact);
                }
            }
        }).subscribe();
    }
}
