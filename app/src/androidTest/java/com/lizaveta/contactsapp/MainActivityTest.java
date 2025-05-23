package com.lizaveta.contactsapp;

import android.Manifest;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.contrib.RecyclerViewActions.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule permissionRuleRead =
            GrantPermissionRule.grant(Manifest.permission.READ_CONTACTS);

    @Rule
    public GrantPermissionRule permissionRuleCall =
            GrantPermissionRule.grant(Manifest.permission.CALL_PHONE);

    @Test
    public void recyclerView_isVisible() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerView_hasItemAtPosition0() {
        onView(withId(R.id.recyclerView))
                .check(matches(hasDescendant(isDisplayed())));
    }

    @Test
    public void clickOnItem_callsCallback() {
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, ViewActions.click()));
    }
}
