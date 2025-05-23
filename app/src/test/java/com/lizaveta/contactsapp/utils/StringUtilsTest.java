package com.lizaveta.contactsapp.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void testInitials_twoWords() {
        assertEquals("JD", StringUtils.getInitials("Liza Vesna"));
    }

    @Test
    public void testInitials_oneWord() {
        assertEquals("J", StringUtils.getInitials("Liza"));
    }

    @Test
    public void testInitials_empty() {
        assertEquals("?", StringUtils.getInitials(""));
    }

    @Test
    public void testInitials_null() {
        assertEquals("?", StringUtils.getInitials(null));
    }

    @Test
    public void testInitials_withSpaces() {
        assertEquals("JD", StringUtils.getInitials("   Liza   Vesna   "));
    }
}