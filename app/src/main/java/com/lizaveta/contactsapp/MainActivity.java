package com.lizaveta.contactsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.lizaveta.contactsapp.adapter.ContactsAdapter;
import com.lizaveta.contactsapp.utils.ContactsHelper;
import com.lizaveta.contactsapp.utils.PermissionHelper;
import com.lizaveta.contactsapp.model.Contact;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_READ_CONTACTS = 1;
    private static final int PERMISSION_CALL_PHONE = 2;

    RecyclerView recyclerView;
    ContactsAdapter adapter;
    ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (PermissionHelper.hasPermission(this, Manifest.permission.READ_CONTACTS)) {
            loadContacts();
        } else {
            PermissionHelper.requestPermission(this, Manifest.permission.READ_CONTACTS, PERMISSION_READ_CONTACTS);
        }

        if (!PermissionHelper.hasPermission(this, Manifest.permission.CALL_PHONE)) {
            PermissionHelper.requestPermission(this, Manifest.permission.CALL_PHONE, PERMISSION_CALL_PHONE);
        }
    }

    private void loadContacts() {
        contacts = ContactsHelper.loadContacts(this);
        adapter = new ContactsAdapter(contacts, this, contact -> makeCall(contact.phone));
        recyclerView.setAdapter(adapter);
    }

    private void makeCall(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            Toast.makeText(this, "Номер не указан", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!PermissionHelper.hasPermission(this, Manifest.permission.CALL_PHONE)) {
            Toast.makeText(this, "Нет разрешения на звонки", Toast.LENGTH_SHORT).show();
            PermissionHelper.requestPermission(this, Manifest.permission.CALL_PHONE, PERMISSION_CALL_PHONE);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_READ_CONTACTS && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadContacts();
        }

        if (requestCode == PERMISSION_CALL_PHONE && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Разрешение на звонки получено", Toast.LENGTH_SHORT).show();
        }
    }
}
