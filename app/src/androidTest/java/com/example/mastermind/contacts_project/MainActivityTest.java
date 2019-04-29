package com.example.mastermind.contacts_project;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {



    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    public ActivityTestRule<MainActivity> activityTestRule=new ActivityTestRule<>(MainActivity.class);
    private MainActivity mcontext=null;
    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(Main2Activity.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {

    mcontext=activityTestRule.getActivity();


    }
@Test
public void testRecycler()
{
    onView(ViewMatchers.withId(R.id.recyclerView))
            .perform(
                    RecyclerViewActions.actionOnItemAtPosition(1, click()));

}
    @Test
    public void testLaunchOfFloatingActionButton()
    {

        onView(withId(R.id.fab)).perform(click());
      Activity Main2Activity=getInstrumentation().waitForMonitorWithTimeout(monitor,10*1000);
            assertNotNull(Main2Activity);
            Main2Activity.finish();


    }

    @After
    public void tearDown() throws Exception {

    mcontext=null;
    }
}