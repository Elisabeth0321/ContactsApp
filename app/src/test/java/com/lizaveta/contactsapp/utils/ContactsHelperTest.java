package com.lizaveta.contactsapp.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.lizaveta.contactsapp.model.Contact;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContactsHelperTest {

    @Test
    public void testLoadContacts_emptyCursor_returnsEmptyList() {
        Context context = mock(Context.class);
        ContentResolver resolver = mock(ContentResolver.class);

        when(context.getContentResolver()).thenReturn(resolver);
        when(resolver.query(any(Uri.class), any(), any(), any(), any())).thenReturn(null);

        assertTrue(ContactsHelper.loadContacts(context).isEmpty());
    }

    @Test
    public void testLoadContacts_contactWithoutPhoneNumber_skipped() {
        Context context = mock(Context.class);
        ContentResolver resolver = mock(ContentResolver.class);
        Cursor contactCursor = mock(Cursor.class);

        when(context.getContentResolver()).thenReturn(resolver);
        when(resolver.query(eq(ContactsContract.Contacts.CONTENT_URI), any(), any(), any(), any()))
                .thenReturn(contactCursor);

        when(contactCursor.getCount()).thenReturn(1);
        when(contactCursor.moveToNext()).thenReturn(true).thenReturn(false);
        when(contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)).thenReturn(0);
        when(contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)).thenReturn(1);
        when(contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI)).thenReturn(2);
        when(contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)).thenReturn(3);
        when(contactCursor.getString(0)).thenReturn("1");
        when(contactCursor.getString(1)).thenReturn("test user");
        when(contactCursor.getString(2)).thenReturn(null);
        when(contactCursor.getInt(3)).thenReturn(0);

        ArrayList<Contact> result = ContactsHelper.loadContacts(context);
        assertTrue(result.isEmpty());

        verify(contactCursor).close();
    }

    @Test
    public void testLoadContacts_withPhoneNumber_addsContact() {
        Context context = mock(Context.class);
        ContentResolver resolver = mock(ContentResolver.class);
        Cursor contactCursor = mock(Cursor.class);
        Cursor phoneCursor = mock(Cursor.class);

        when(context.getContentResolver()).thenReturn(resolver);
        when(resolver.query(eq(ContactsContract.Contacts.CONTENT_URI), any(), any(), any(), any()))
                .thenReturn(contactCursor);

        when(contactCursor.getCount()).thenReturn(1);
        when(contactCursor.moveToNext()).thenReturn(true).thenReturn(false);
        when(contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)).thenReturn(0);
        when(contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)).thenReturn(1);
        when(contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI)).thenReturn(2);
        when(contactCursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)).thenReturn(3);
        when(contactCursor.getString(0)).thenReturn("1");
        when(contactCursor.getString(1)).thenReturn("Liza");
        when(contactCursor.getString(2)).thenReturn("photo_uri");
        when(contactCursor.getInt(3)).thenReturn(1);
        when(resolver.query(eq(ContactsContract.CommonDataKinds.Phone.CONTENT_URI), any(), any(), any(), any()))
                .thenReturn(phoneCursor);

        when(phoneCursor.moveToNext()).thenReturn(true).thenReturn(false);
        int phoneNumberIndex = 0;
        when(phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)).thenReturn(phoneNumberIndex);
        when(phoneCursor.getString(phoneNumberIndex)).thenReturn("123456789");

        ArrayList<Contact> result = ContactsHelper.loadContacts(context);
        assertEquals(1, result.size());
        assertEquals("Liza", result.get(0).getName());
        assertEquals("123456789", result.get(0).getPhone());
        assertEquals("photo_uri", result.get(0).getPhotoUri());

        verify(contactCursor).close();
        verify(phoneCursor).close();
    }
}

