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
}

