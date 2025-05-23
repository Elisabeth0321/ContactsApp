package com.lizaveta.contactsapp.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class PermissionHelperTest {

    @Test
    public void testHasPermission_granted() {
        Activity mockActivity = Mockito.mock(Activity.class);

        try (MockedStatic<ContextCompat> mocked = Mockito.mockStatic(ContextCompat.class)) {
            mocked.when(() -> ContextCompat.checkSelfPermission(mockActivity, "android.permission.CALL_PHONE"))
                    .thenReturn(PackageManager.PERMISSION_GRANTED);

            assertTrue(PermissionHelper.hasPermission(mockActivity, "android.permission.CALL_PHONE"));
        }
    }

    @Test
    public void testHasPermission_denied() {
        Activity mockActivity = Mockito.mock(Activity.class);

        try (MockedStatic<ContextCompat> mocked = Mockito.mockStatic(ContextCompat.class)) {
            mocked.when(() -> ContextCompat.checkSelfPermission(mockActivity, "android.permission.READ_CONTACTS"))
                    .thenReturn(PackageManager.PERMISSION_DENIED);

            assertFalse(PermissionHelper.hasPermission(mockActivity, "android.permission.READ_CONTACTS"));
        }
    }
}