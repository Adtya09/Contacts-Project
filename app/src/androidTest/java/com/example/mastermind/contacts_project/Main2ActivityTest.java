package com.example.mastermind.contacts_project;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class Main2ActivityTest {
@Rule
public ActivityTestRule<Main2Activity> activityTestRule=new ActivityTestRule<>(Main2Activity.class);
    private String name="Aditya";
    private String email="athemastermind9@gmail.com";
    private String phone="816953096";

    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void inputData()
    {
        onView(withId(R.id.name)).perform(typeText(name));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.name))
                .check(matches(withText(containsString("Aditya"))));


    }

    @After
    public void tearDown() throws Exception {
    }
}