package com.lizaveta.contactsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lizaveta.contactsapp.R;
import com.lizaveta.contactsapp.model.Contact;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private final List<Contact> contactList;
    private final Context context;
    private final OnContactClickListener listener;

    public ContactsAdapter(List<Contact> contacts, Context context, OnContactClickListener listener) {
        this.contactList = contacts;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.textName.setText(contact.name);
        holder.textPhone.setText(contact.phone);

        if (contact.photoUri != null) {
            holder.textInitials.setVisibility(View.GONE);
            Glide.with(context)
                    .load(Uri.parse(contact.photoUri))
                    .circleCrop()
                    .into(holder.imageView);
        } else {
            holder.textInitials.setVisibility(View.VISIBLE);
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_bg));
            holder.textInitials.setText(getInitials(contact.name));
        }
        holder.itemView.setOnClickListener(v -> listener.onContactClick(contact));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    private String getInitials(String name) {
        String[] parts = name.trim().split(" ");
        if (parts.length >= 2) {
            return ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
        } else if (parts.length == 1 && !parts[0].isEmpty()) {
            return ("" + parts[0].charAt(0)).toUpperCase();
        }
        return "?";
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textPhone;
        ImageView imageView;
        TextView textInitials;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textPhone = itemView.findViewById(R.id.textPhone);
            imageView = itemView.findViewById(R.id.imageView);
            textInitials = itemView.findViewById(R.id.textInitials);
        }
    }
}
