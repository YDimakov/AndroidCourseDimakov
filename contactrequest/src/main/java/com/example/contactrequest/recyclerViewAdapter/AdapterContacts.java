package com.example.contactrequest.recyclerViewAdapter;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactrequest.Contact;
import com.example.contactrequest.MainActivity;
import com.example.contactrequest.R;

import java.util.ArrayList;

public class AdapterContacts extends RecyclerView.Adapter<AdapterContacts.MyViewHolder> {

    private Context context;
    private ArrayList<Contact> contacts;
    private MainActivity mainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView informationTextView;
        public ImageView imageViewPhotoContact;


        public MyViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.textViewName);
            informationTextView = view.findViewById(R.id.textViewPhoneOrEmail);
            imageViewPhotoContact = view.findViewById(R.id.photoContact);

        }
    }

    public AdapterContacts(Context context, ArrayList<Contact> contacts, MainActivity mainActivity) {
        this.context = context;
        this.contacts = contacts;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_contact, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Contact contact = contacts.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.informationTextView.setText(contact.getInformation());
        holder.imageViewPhotoContact.setImageResource(contact.getPhoto());
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }
}
