package com.example.myproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;


public class AdapterListAnimal extends RecyclerView.Adapter<AdapterListAnimal.RecyclerViewHolder> {
    private List<Animal> arrayListAnimals;
    private OnNoteListener onNoteListener;

    public AdapterListAnimal(List<Animal> arrayListAnimals, OnNoteListener onNoteListener) {
        this.arrayListAnimals = arrayListAnimals;
        this.onNoteListener = onNoteListener;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements OnNoteListener {
        private TextView nameText;
        private ImageView photoImage;
        private TextView ageText;
        private TextView typeText;
        private TextView colorText;
        private TextView commentText;
        private TextView phoneText;
        private OnNoteListener onNoteListener;


        public RecyclerViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;
            nameText = itemView.findViewById(R.id.nameTextViewAnimals);
            photoImage = itemView.findViewById(R.id.photoTextViewAnimal);
            ageText = itemView.findViewById(R.id.ageTextViewAnimal);
            typeText = itemView.findViewById(R.id.typeTextViewAnimal);
            colorText = itemView.findViewById(R.id.colorTextViewAnimal);
            commentText = itemView.findViewById(R.id.commentTextViewAnimal);
            phoneText = itemView.findViewById(R.id.phoneTextViewAnimal);
        }

        @Override
        public void onNoteClick(int position) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_animal, parent, false);
        return new RecyclerViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Animal animal = arrayListAnimals.get(position);
        holder.nameText.setText(animal.getNameAnimal());
        holder.ageText.setText(animal.getAgeAnimal());
        holder.typeText.setText(animal.getTypeAnimal());
        holder.colorText.setText(animal.getColorAnimal());
        holder.commentText.setText(animal.getCommentAnimal());
        holder.phoneText.setText(animal.getPhoneAnimal());
        holder.photoImage.setVisibility(View.VISIBLE);
        Glide.with(holder.photoImage.getContext())
                .load(animal.getPhotoAnimal())
                .into(holder.photoImage);
    }

    @Override
    public int getItemCount() {
        return arrayListAnimals != null ? arrayListAnimals.size() : 0;
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
