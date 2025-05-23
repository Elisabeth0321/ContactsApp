package com.lizaveta.contactsapp.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.lizaveta.contactsapp.model.Contact;

import java.util.ArrayList;
import java.util.Comparator;

public class ContactsHelper {

    public static ArrayList<Contact> loadContacts(Context context) {
        ArrayList<Contact> contacts = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = cr.query(uri, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                String photoUri = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI));
                int hasPhoneNumber = cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null
                    );

                    if (phoneCursor != null) {
                        while (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(
                                    phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            );
                            contacts.add(new Contact(name, phoneNumber, photoUri));
                            break; //только 1 номер
                        }
                        phoneCursor.close();
                    }
                }
            }
            cursor.close();
        }

        contacts.sort(Comparator.comparing(c -> c.getName().toLowerCase()));

        return contacts;
    }
}
