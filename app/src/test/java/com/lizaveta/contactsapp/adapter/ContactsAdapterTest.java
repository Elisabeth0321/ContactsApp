package com.lizaveta.contactsapp.adapter;

import com.lizaveta.contactsapp.model.Contact;

import org.junit.Test;
import java.util.Collections;

import static org.junit.Assert.*;

public class ContactsAdapterTest {

    @Test
    public void testGetItemCount_returnsCorrectSize() {
        ContactsAdapter adapter = new ContactsAdapter(
                Collections.singletonList(new Contact("Alice", "123", null)),
                null,
                contact -> {}
        );

        assertEquals(1, adapter.getItemCount());
    }

    @Test
    public void testOnContactClick_calledOnItemClick() {
        Contact testContact = new Contact("Bob", "456", null);
        final boolean[] clicked = {false};

        ContactsAdapter adapter = new ContactsAdapter(
                Collections.singletonList(testContact),
                null,
                contact -> {
                    clicked[0] = true;
                    assertEquals("Bob", contact.getName());
                }
        );

        adapter.listener.onContactClick(testContact);
        assertTrue(clicked[0]);
    }

}