package com.example.contacts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.Contact;
import com.example.contacts.R;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<Contact> arrayListContact;
    private OnNoteListener onNoteListener;

    public RecyclerViewAdapter(ArrayList<Contact> arrayListContact, OnNoteListener onNoteListener) {
        this.arrayListContact = arrayListContact;
        this.onNoteListener = onNoteListener;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameTextViewRecyclerViewAdapter;// установка переменных имени,телефона и почты.
        private TextView phoneOrEmailTextViewRecyclerViewAdapter;
        private ImageView photoContactImageViewRecyclerViewAdapter;
        private ImageButton imageButtonEdit;
        private ImageButton imageButtonClear;
        private OnNoteListener onNoteListener;


        private void bind(Contact contact) {
            nameTextViewRecyclerViewAdapter.setText(contact.getName());
            phoneOrEmailTextViewRecyclerViewAdapter.setText(contact.getPhoneOrEmail());
            photoContactImageViewRecyclerViewAdapter.setImageResource(contact.getPhotoContact());

        }

        private RecyclerViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;
            nameTextViewRecyclerViewAdapter = itemView.findViewById(R.id.textViewName);// присоеденили разметку и код имени телефона и email
            phoneOrEmailTextViewRecyclerViewAdapter = itemView.findViewById(R.id.textViewPhoneOrEmail);
            photoContactImageViewRecyclerViewAdapter = itemView.findViewById(R.id.photoContact);
            imageButtonEdit = itemView.findViewById(R.id.editButton);
            imageButtonClear = itemView.findViewById(R.id.clearButton);
            imageButtonEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_recycler_view, parent, false);
        return new RecyclerViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        holder.bind(arrayListContact.get(position));
        holder.imageButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayListContact.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListContact != null ? arrayListContact.size() : 0;
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

}

